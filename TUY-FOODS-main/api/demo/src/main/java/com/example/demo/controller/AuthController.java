package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = authService.register(user);
            return ResponseEntity.ok(Map.of(
                    "message", "Đăng ký thành công",
                    "userId", saved.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            User user = authService.login(body.get("email"), body.get("password"));
            return ResponseEntity.ok(Map.of(
                    "message", "Đăng nhập thành công",
                    "userId", user.getId(),
                    "role", user.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}