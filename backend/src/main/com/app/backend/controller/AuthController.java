package com.app.backend.controller;

import com.app.backend.dto.loginRequest;
import com.app.backend.dto.loginResponse;
import com.app.backend.model.User;                                          
import com.app.backend.Repository.|UserRepository;
import com.app.backend.security.JwTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.username|passwordauthenticationtoken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.securitycontextholder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody loginRequest loginRequest) {
        try{
        Authentication authentication = authenticationManager.authenticate(
                new usernamePasswordAuthenticationToken (
                    loginRequest.getUsername(),
                    loginRequest.getPassword()))
                    SecurityContextHolder.getContext().
                    setAuthentication(authentication);

                    User user = userRepository.findByUsername(loginRequest.getUsername()).orElseTjrow(()-> new RuntimeException("Usuario no encontrado"));


                    return ResponseEntity.ok(new loginResponse(
                       jwt, user ));
        } catch (Exception e) {
        return ResponseEntity.badRequest().body("{\"error\": \"Credenciales inválidas\"}");
        }
    }
}