/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.prefs.Preferences;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author ASUS
 */
@Component
public class SignUp {
    /*public static boolean SignUpUser(String email, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }*/
    private static final String API_KEY = "AIzaSyBYbkxO35ZW_HusdP-AR6rGA-GggfrOi08";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    public static boolean SignUpAdmin(String email, String password) {
        try {
            String payload = new JSONObject()
                    .put("email", email)
                    .put("password", password)
                    .put("returnSecureToken", true)
                    .toString();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .uri(URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY))
                    .setHeader("Content-Type", "application/json; charset=UTF-8")
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JSONObject data = new JSONObject(response.body());
                String idToken = data.getString("idToken");
                String refreshToken = data.getString("refreshToken");
                String localId = data.getString("localId");
                
                Preferences prefs = Preferences.userRoot().node("DAGKNT106");
                prefs.put("idToken", idToken);
                prefs.put("refreshToken", refreshToken);
                prefs.put("localId", localId);
                
                return true;
            } else {
                JSONObject error = new JSONObject(response.body()).getJSONObject("error");
                
                return false;
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String errorDetails = sw.toString();
            
            return false;
        }
    }
}
