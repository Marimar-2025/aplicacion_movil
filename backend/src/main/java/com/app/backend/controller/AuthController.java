package com.app.backend.controller;

import com.app.backend.dto.LoginRequest;
import com.app.backend.dto.LoginResponse;
import com.app.backend.model.User;
import com.app.backend.repository.UserRepository;
import com.app.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("=== Intento de login ===");
            System.out.println("Username recibido: " + loginRequest.getUsername());
            System.out.println("Password recibido: " + loginRequest.getPassword());
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String jwt = tokenProvider.generateToken(authentication);

            System.out.println("Login exitoso para: " + loginRequest.getUsername());
            return ResponseEntity.ok(new LoginResponse(jwt, user));
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\": \"Credenciales inválidas\"}");
        }
    }
}