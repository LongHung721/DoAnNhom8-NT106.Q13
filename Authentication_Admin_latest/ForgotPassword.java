/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author ASUS
 */
@Component
public class ForgotPassword {
    private static final String API_KEY = "AIzaSyBYbkxO35ZW_HusdP-AR6rGA-GggfrOi08";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    

    public static boolean ForgotPasswordAdmin(String email) {
        try {
            String payload = new JSONObject()
                    .put("requestType", "PASSWORD_RESET")
                    .put("email", email)
                    .toString();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=" + API_KEY))
                    .setHeader("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .timeout(Duration.ofSeconds(20))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return (response.statusCode() == 200);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String errorDetails = sw.toString();
            
            return false;
        }
    }
}
