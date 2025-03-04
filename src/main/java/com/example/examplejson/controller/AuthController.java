package com.example.examplejson.controller;

import com.example.examplejson.payload.LoginDto;
import com.example.examplejson.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
        System.out.println("Login request: " + loginDto.getUsername());
        String s = jwtProvider.generateToken(loginDto.getUsername());
        System.out.println("new token: " + s);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            System.out.println("Authenticated user: " + authentication);

            if (authentication == null) {
                return ResponseEntity.status(401).body("Authentication failed");
            }

            String token = jwtProvider.generateToken(authentication.getName());
            System.out.println("Generated token: " + token);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
