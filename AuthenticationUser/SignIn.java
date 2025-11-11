/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106client;
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
    private static final String BASE_URL = "http://localhost:8080/api/auth";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    public static boolean SignInUser(String username, String password) {
        try{
            String payload = new JSONObject()
                    .put("username", username)
                    .put("password", password)
                    .toString();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .uri(URI.create(BASE_URL + "/login"))
                    .setHeader("Content-Type", "application/json; charset=UTF-8")
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                
                JSONObject data = new JSONObject(response.body());
                String token = data.getString("token");
                
                
                Preferences prefs = Preferences.userRoot().node("DAGKNT106CLIENT");
                prefs.put("token", token);
               
                return true;
            } else {
                JSONObject error = new JSONObject(response.body()).getJSONObject("error");
                
                return false;
            }
        } catch (Exception e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String errorDetails = sw.toString();
            
            return false;
        }
    }
}
