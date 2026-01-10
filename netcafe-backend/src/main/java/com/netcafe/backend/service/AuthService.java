package com.netcafe.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class AuthService {

    private final Firestore db;
    private final String authCollection;

    public AuthService(Firestore db,
                       @Value("${app.firestore.authCollection}") String authCollection) {
        this.db = db;
        this.authCollection = authCollection;
    }

    public Optional<Map<String, Object>> login(String username, String password) throws ExecutionException, InterruptedException {
        // Compatible with your old schema: Khach_Hang { TenTK, MatKhau, LoaiKH }
        ApiFuture<QuerySnapshot> future = db.collection(authCollection)
                .whereEqualTo("TenTK", username)
                .limit(1)
                .get();

        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        if (docs.isEmpty()) return Optional.empty();

        DocumentSnapshot doc = docs.get(0);
        String hashed = doc.getString("MatKhau");
        if (hashed == null) return Optional.empty();

        if (!BCrypt.checkpw(password, hashed)) return Optional.empty();

        Map<String, Object> result = new HashMap<>();
        result.put("id", doc.getId());
        result.put("username", doc.getString("TenTK"));
        result.put("role", Optional.ofNullable(doc.getString("LoaiKH")).orElse("USER"));
        return Optional.of(result);
    }

    public boolean resetPassword(String idCard, String newPassword) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(authCollection)
                .whereEqualTo("SoCCCD", idCard)
                .limit(1)
                .get();

        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        if (docs.isEmpty()) return false;

        DocumentReference docRef = docs.get(0).getReference();
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        ApiFuture<WriteResult> wr = docRef.update("MatKhau", hashed);
        wr.get();
        return true;
    }
}
