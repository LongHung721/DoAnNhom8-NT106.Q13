/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.*;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author ASUS
 */
@Service
public class AuthService {
    private final Firestore db;
    
    @Autowired
    public AuthService(Firestore db) {
        this.db = db;
    }

    public Map<String, Object> login(String username, String password) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("Khach_Hang")
                .whereEqualTo("TenTK", username)
                .limit(1)
                .get();

        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        if (docs.isEmpty()) return null;

        DocumentSnapshot doc = docs.get(0);
        String hashed = doc.getString("MatKhau");
        if (hashed == null) return null;

        if (BCrypt.checkpw(password, hashed)) {
            Map<String, Object> result = new HashMap<>();
            result.put("idKH", doc.getId());
            result.put("TenTK", doc.getString("TenTK"));
            result.put("LoaiKH", doc.getString("LoaiKH"));
            result.put("HoTen", doc.getString("HoTen"));
            // Một số field có thể null tuỳ DB, vẫn trả về để client dùng.
            result.put("SoGio", doc.get("SoGio"));
            result.put("TongTienDaNap", doc.get("TongTienDaNap"));
            return result;
        } else {
            return null;
        }
    }

    public boolean resetPassword(String idCard, String newPassword) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("Khach_Hang")
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
