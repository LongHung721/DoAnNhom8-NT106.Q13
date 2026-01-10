package com.netcafe.backend.controller;

import com.netcafe.backend.dto.ErrorResponse;
import com.netcafe.backend.dto.LoginRequest;
import com.netcafe.backend.dto.ResetPasswordRequest;
import com.netcafe.backend.security.JwtUtil;
import com.netcafe.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            var userOpt = authService.login(req.getUsername(), req.getPassword());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body(new ErrorResponse("Invalid credentials"));
            }
            Map<String, Object> user = userOpt.get();
            String userId = (String) user.get("id");
            String role = String.valueOf(user.getOrDefault("role", "USER"));

            String token = jwtUtil.generateToken(
                    userId,
                    Map.of("username", user.get("username"), "role", role)
            );

            // IMPORTANT: match your frontend SignIn.java expectation: JSON must contain token
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Server error"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        try {
            boolean ok = authService.resetPassword(req.getIdCard(), req.getNewPassword());
            if (!ok) {
                return ResponseEntity.status(404).body(new ErrorResponse("Account not found"));
            }
            return ResponseEntity.ok(Map.of("status", "OK"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Server error"));
        }
    }
}
