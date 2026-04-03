package com.nexaride.api.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexaride.api.dto.LoginRequest;
import com.nexaride.api.model.User;
import com.nexaride.api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    // ✅ REGISTER (still basic, but acceptable for now)
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(service.register(user));
    }

    // 🔥 PROPER LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {

        String token = service.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(token);
    }
}