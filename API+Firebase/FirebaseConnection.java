/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ASUS
 */
@Configuration
public class FirebaseConnection {
    @Bean
    public static Firestore InitializeFirebase(){
        try {
            FileInputStream serviceAccount = new FileInputStream("javaauthapp-bd5a1-firebase-adminsdk-fbsvc-6c26b3e1b7.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Kết nối Firebase thành công!");
            }
            return FirestoreClient.getFirestore();
        } catch (IOException e) {
            System.out.println("Lỗi kết nối Firebase: " + e.getMessage());
            return null;
        }
    }
    
    public static void DeleteFirebaseApp(){
        for (FirebaseApp app : FirebaseApp.getApps()){
            app.delete();
        }
    }
}
