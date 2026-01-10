package com.mycompany.doangiuakynt106.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mycompany.doangiuakynt106.api.dto.MachineState;
import com.mycompany.doangiuakynt106.api.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * RTDB publisher: backend đẩy trạng thái máy / order / chat lên Firebase Realtime Database.
 *
 * Paths:
 *  - /machines/{machineId}
 *  - /orders/{orderId}
 *  - /chat/{machineId}/{messageId}
 */
@Service
public class RtdbService {

    private final FirebaseDatabase rtdb;

    public RtdbService(FirebaseDatabase rtdb) {
        this.rtdb = rtdb;
    }

    private DatabaseReference root() {
        return rtdb.getReference();
    }

    public void upsertMachine(MachineState s) {
        try {
            Map<String, Object> doc = new HashMap<>();
            doc.put("machineId", s.getMachineId());
            doc.put("status", s.getStatus() == null ? "LOCKED" : s.getStatus().name());
            doc.put("username", s.getUsername());
            doc.put("userId", s.getUserId());
            doc.put("remainingSeconds", s.getRemainingSeconds());
            doc.put("startedAt", s.getStartedAt() == null ? null : s.getStartedAt().toString());
            doc.put("updatedAt", Instant.now().toString());
            root().child("machines").child(s.getMachineId()).setValueAsync(doc);
        } catch (Exception ignored) {
        }
    }

    public void upsertOrder(OrderDto o) {
        try {
            if (o.getId() == null || o.getId().isBlank()) return;
            Map<String, Object> doc = new HashMap<>();
            doc.put("id", o.getId());
            doc.put("machineId", o.getMachineId());
            doc.put("note", o.getNote());
            doc.put("status", o.getStatus() == null ? "NEW" : o.getStatus().name());
            doc.put("createdAt", o.getCreatedAt());
            doc.put("updatedAt", Instant.now().toString());
            doc.put("items", o.getItems());
            root().child("orders").child(o.getId()).setValueAsync(doc);
        } catch (Exception ignored) {
        }
    }

    public void pushChat(String machineId, String from, String message) {
        try {
            String key = root().child("chat").child(machineId).push().getKey();
            if (key == null) return;
            Map<String, Object> doc = new HashMap<>();
            doc.put("from", from);
            doc.put("message", message);
            doc.put("createdAt", Instant.now().toString());
            root().child("chat").child(machineId).child(key).setValueAsync(doc);
        } catch (Exception ignored) {
        }
    }
}
