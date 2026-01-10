package com.mycompany.doangiuakynt106client.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Client listen RTDB realtime: /machines/{machineId}
 *
 * Lưu ý: vì đang là đồ án local, client dùng Firebase Admin SDK (cần service account json).
 * Nếu triển khai thực tế, KHÔNG nên ship admin credentials xuống máy khách.
 */
public class FirebaseRtdbClient {

    private static final AtomicBoolean inited = new AtomicBoolean(false);

    // TODO: thay URL này bằng URL RTDB của project Firebase của bạn
    // Firebase Console -> Realtime Database -> copy URL ở phía trên.
    private static final String DATABASE_URL = "REPLACE_ME";

    public static void initOnce() {
        if (inited.compareAndSet(false, true)) {
            try (FileInputStream serviceAccount = new FileInputStream("javaauthapp-bd5a1-firebase-adminsdk-fbsvc-006a9f97b4.json")) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl(DATABASE_URL)
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }
            } catch (Exception e) {
                System.out.println("[RTDB] init error: " + e.getMessage());
            }
        }
    }

    public static DatabaseReference root() {
        initOnce();
        return FirebaseDatabase.getInstance().getReference();
    }

    public interface MachineListener {
        void onChange(Map<String, Object> machine);
    }

    public static ValueEventListener listenMachine(String machineId, MachineListener cb) {
        DatabaseReference ref = root().child("machines").child(machineId);
        ValueEventListener lis = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object v = snapshot.getValue();
                if (v instanceof Map<?, ?> m) {
                    //noinspection unchecked
                    cb.onChange((Map<String, Object>) m);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("[RTDB] listen cancelled: " + error.getMessage());
            }
        };
        ref.addValueEventListener(lis);
        return lis;
    }

    public static void stopListenMachine(String machineId, ValueEventListener lis) {
        try {
            if (lis == null) return;
            root().child("machines").child(machineId).removeEventListener(lis);
        } catch (Exception ignored) {
        }
    }
}
