/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import org.springframework.security.crypto.bcrypt.BCrypt;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author ASUS
 */
public class AppFeature {
    public static boolean AddUser(String fullName, String idCard, String username, String password){                          
         try {
            
            Firestore db = FirestoreClient.getFirestore();
            
            ApiFuture<QuerySnapshot> future = db.collection("Khach_Hang")
                .orderBy(FieldPath.documentId(), Query.Direction.DESCENDING)
                .limit(1)
                .get();

            List<QueryDocumentSnapshot> docs = future.get().getDocuments();

            int idKH;
            if (docs.isEmpty()) {
                idKH = 1;
            } else {
                idKH = Integer.parseInt(docs.get(0).getId()) + 1;
            }
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            String customerType = "Thuong";
            int hoursRemaining = 0;
            int totalMoney = 0;
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("HoTen", fullName);
            userData.put("SoCCCD", idCard);
            userData.put("TenTK", username);
            userData.put("MatKhau", hashed);
            userData.put("SoGio", hoursRemaining);
            userData.put("TongTienDaNap", totalMoney);
            userData.put("LoaiKH", customerType);

            DocumentReference docRef = db.collection("Khach_Hang").document(String.valueOf(idKH));

            ApiFuture<?> result = docRef.set(userData);
            result.get(); 

            return true;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String errorDetails = sw.toString();
            
            System.out.println(errorDetails);
            
            return false;
        }
    }
}
