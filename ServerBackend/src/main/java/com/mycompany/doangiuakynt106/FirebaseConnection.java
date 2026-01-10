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
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author ASUS
*/

@Configuration
public class FirebaseConnection {

    private final Environment env;

    public FirebaseConnection(Environment env) {
        this.env = env;
    }

    @Bean
    public Firestore InitializeFirebase(){
        try {
            FileInputStream serviceAccount = new FileInputStream("javaauthapp-bd5a1-firebase-adminsdk-fbsvc-006a9f97b4.json");

            // IMPORTANT: bạn phải cấu hình đúng URL Realtime Database của project.
            // Có thể set trong src/main/resources/application.properties:
            // firebase.database-url=...
            // hoặc set ENV FIREBASE_DATABASE_URL
            String dbUrl = System.getenv("FIREBASE_DATABASE_URL");
            if (dbUrl == null || dbUrl.isBlank()) {
                dbUrl = env.getProperty("firebase.database-url", "");
            }

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(dbUrl == null || dbUrl.isBlank()
                            ? "https://javaauthapp-bd5a1-default-rtdb.firebaseio.com" // placeholder
                            : dbUrl)
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

    @Bean
    public FirebaseDatabase firebaseDatabase() {
        // FirebaseApp đã được init trong InitializeFirebase()
        return FirebaseDatabase.getInstance();
    }
    
    public static void DeleteFirebaseApp(){
        for (FirebaseApp app : FirebaseApp.getApps()){
            app.delete();
        }
    }
}
