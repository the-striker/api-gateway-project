package com.example.gateway.controller;

import com.example.gateway.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final String MOCK_USER = "admin";
    private final String MOCK_PASS = "password";

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");

        if (MOCK_USER.equals(username) && MOCK_PASS.equals(password)) {
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                    .signWith(JwtUtil.getSecretKey())
                    .compact();
            return Map.of("token", token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
