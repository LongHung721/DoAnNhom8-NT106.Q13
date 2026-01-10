package com.mycompany.doangiuakynt106.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.mycompany.doangiuakynt106.api.dto.MachineState;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MachineService {

    private final Firestore db;
    private final RtdbService rtdb;
    private final ConcurrentHashMap<String, MachineState> machines = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "machine-ticker");
        t.setDaemon(true);
        return t;
    });
    private final AtomicBoolean tickerStarted = new AtomicBoolean(false);

    public MachineService(Firestore db, RtdbService rtdb) {
        this.db = db;
        this.rtdb = rtdb;
        startTickerOnce();
    }

    private void startTickerOnce() {
        if (tickerStarted.compareAndSet(false, true)) {
            scheduler.scheduleAtFixedRate(this::tick, 1, 1, TimeUnit.SECONDS);
        }
    }

    private void tick() {
        try {
            for (MachineState s : machines.values()) {
                if (s.getStatus() == MachineState.Status.RUNNING && s.getRemainingSeconds() > 0) {
                    s.setRemainingSeconds(s.getRemainingSeconds() - 1);
                    s.setUpdatedAt(Instant.now());
                    if (s.getRemainingSeconds() <= 0) {
                        // Hết giờ => tự khoá máy
                        s.setRemainingSeconds(0);
                        s.setStatus(MachineState.Status.LOCKED);
                        s.setUserId(null);
                        s.setUsername(null);
                        s.setStartedAt(null);
                    }
                    // publish mỗi giây để client nghe realtime
                    rtdb.upsertMachine(s);
                }
            }
        } catch (Exception ignored) {
        }
    }

    public MachineState getOrCreate(String machineId) {
        return machines.computeIfAbsent(machineId, MachineState::new);
    }

    public List<MachineState> listMachines() {
        return machines.values().stream().sorted((a, b) -> a.getMachineId().compareToIgnoreCase(b.getMachineId())).toList();
    }

    /**
     * Mở máy cho user nếu còn số dư/giờ.
     * Quy ước: nếu field SoGio là số giờ còn lại (double/int), thì remainingSeconds = SoGio * 3600.
     */
    public MachineState startSession(String machineId, String username) throws Exception {
        MachineState s = getOrCreate(machineId);
        if (s.getStatus() == MachineState.Status.RUNNING) {
            // Máy đang có người dùng
            return s;
        }

        ApiFuture<QuerySnapshot> future = db.collection("Khach_Hang")
                .whereEqualTo("TenTK", username)
                .limit(1)
                .get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        if (docs.isEmpty()) {
            throw new IllegalArgumentException("user_not_found");
        }
        DocumentSnapshot doc = docs.get(0);
        Object soGioObj = doc.get("SoGio");
        long remaining = 0;
        if (soGioObj instanceof Number n) {
            remaining = Math.max(0, Math.round(n.doubleValue() * 3600.0));
        }

        if (remaining <= 0) {
            throw new IllegalStateException("insufficient_time");
        }

        s.setStatus(MachineState.Status.RUNNING);
        s.setUserId(doc.getId());
        s.setUsername(username);
        s.setRemainingSeconds(remaining);
        s.setStartedAt(Instant.now());
        s.setUpdatedAt(Instant.now());
        rtdb.upsertMachine(s);
        return s;
    }

    public MachineState lockMachine(String machineId) {
        MachineState s = getOrCreate(machineId);
        s.setStatus(MachineState.Status.LOCKED);
        s.setRemainingSeconds(0);
        s.setUserId(null);
        s.setUsername(null);
        s.setStartedAt(null);
        s.setUpdatedAt(Instant.now());
        rtdb.upsertMachine(s);
        return s;
    }

    public MachineState pause(String machineId) {
        MachineState s = getOrCreate(machineId);
        if (s.getStatus() == MachineState.Status.RUNNING) {
            s.setStatus(MachineState.Status.PAUSED);
            s.setUpdatedAt(Instant.now());
            rtdb.upsertMachine(s);
        }
        return s;
    }

    public MachineState resume(String machineId) {
        MachineState s = getOrCreate(machineId);
        if (s.getStatus() == MachineState.Status.PAUSED) {
            s.setStatus(MachineState.Status.RUNNING);
            s.setUpdatedAt(Instant.now());
            rtdb.upsertMachine(s);
        }
        return s;
    }

    public Map<String, Object> switchMachine(String fromMachineId, String toMachineId) {
        MachineState from = getOrCreate(fromMachineId);
        MachineState to = getOrCreate(toMachineId);

        if (from.getStatus() == MachineState.Status.LOCKED) {
            throw new IllegalStateException("from_machine_locked");
        }
        if (to.getStatus() != MachineState.Status.LOCKED) {
            throw new IllegalStateException("to_machine_busy");
        }

        to.setStatus(from.getStatus());
        to.setUserId(from.getUserId());
        to.setUsername(from.getUsername());
        to.setRemainingSeconds(from.getRemainingSeconds());
        to.setStartedAt(from.getStartedAt());
        to.setUpdatedAt(Instant.now());
        rtdb.upsertMachine(to);

        lockMachine(fromMachineId);

        return Map.of(
                "from", from,
                "to", to
        );
    }
}
