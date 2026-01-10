package com.netcafe.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.serviceAccountPath:}")
    private String serviceAccountPath;

    @Bean
    public Firestore firestore() throws Exception {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccountStream = null;

            if (serviceAccountPath != null && !serviceAccountPath.isBlank()) {
                // absolute path or "classpath:xxx.json"
                if (serviceAccountPath.startsWith("classpath:")) {
                    String cp = serviceAccountPath.substring("classpath:".length());
                    serviceAccountStream = new ClassPathResource(cp).getInputStream();
                } else {
                    serviceAccountStream = new FileInputStream(serviceAccountPath);
                }
            } else {
                // Fallback: GOOGLE_APPLICATION_CREDENTIALS environment var
                // Firebase Admin will use Application Default Credentials if available.
                serviceAccountStream = null;
            }

            FirebaseOptions.Builder builder = FirebaseOptions.builder();

            if (serviceAccountStream != null) {
                builder.setCredentials(GoogleCredentials.fromStream(serviceAccountStream));
            } else {
                builder.setCredentials(GoogleCredentials.getApplicationDefault());
            }

            FirebaseOptions options = builder.build();
            FirebaseApp.initializeApp(options);
        }
        return FirestoreClient.getFirestore();
    }
}
