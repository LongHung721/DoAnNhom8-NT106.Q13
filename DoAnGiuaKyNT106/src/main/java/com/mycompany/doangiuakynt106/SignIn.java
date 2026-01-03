/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.prefs.Preferences;
import java.time.Duration;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.io.StringWriter;
/**
 *
 * @author ASUS
 */
public class SignIn {
    private static final String API_KEY = "AIzaSyBYbkxO35ZW_HusdP-AR6rGA-GggfrOi08";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static boolean SignInAdmin(String email, String password) {
        try {
            String payload = new JSONObject()
                    .put("email", email)
                    .put("password", password)
                    .put("returnSecureToken", true)
                    .toString();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .uri(URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY))
                    .setHeader("Content-Type", "application/json; charset=UTF-8")
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                //FirebaseConnection.InitializeFirebase();
                
                JSONObject data = new JSONObject(response.body());
                String idToken = data.getString("idToken");
                String refreshToken = data.getString("refreshToken");
                
                Preferences prefs = Preferences.userRoot().node("DAGKNT106");
                prefs.put("idToken", idToken);
                prefs.put("refreshToken", refreshToken);
                
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
