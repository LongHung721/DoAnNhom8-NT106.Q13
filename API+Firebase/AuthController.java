/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author ASUS
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            if (username == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "username and password required"));
            }

            Map<String, Object> user = authService.login(username, password);
            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            
            String token = JWTUtil.generateToken(username);
            user.put("token", token);
            user.put("expiresIn", 3600);
            
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "internal_error"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String idCard = body.get("idCard");
            String newPassword = body.get("newPassword");
            if (idCard == null || newPassword == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "idCard and newPassword required"));
            }

            boolean ok = authService.resetPassword(idCard, newPassword);
            if (!ok) {
                return ResponseEntity.status(404).body(Map.of("error", "user_not_found"));
            }
            return ResponseEntity.ok(Map.of("message", "password_reset"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "internal_error"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody(required = false) Map<String, String> body) {
        return ResponseEntity.ok(Map.of("message", "logged_out"));
    }
}
