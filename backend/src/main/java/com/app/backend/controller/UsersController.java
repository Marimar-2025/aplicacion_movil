package com.app.backend.controller;

import com.app.backend.dto.UserCreateRequest;
import com.app.backend.dto.UserUpdateRequest;
import com.app.backend.model.User;
import com.app.backend.service.UserService;
import com.app.backend.dto.MessagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.Http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;      

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")

public class UsersController {
    @Autowired
    private UserService userService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','COORDINADOR')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));     
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
    try {
     return ResponseEntity.ok(userService.update(id, request));
    }catch (RuntimeException e){
        return ResponseEntity.badRequest().body(new MessagerResponse(e.getMessage()));
        if (e.getMessage().equals("No tiene permisos")) {
            return ResponseEntity.status(404).body(new MessagerResponse(e.getMessage()));
        }  
         return ResponseEntity.badRequest().body(new MessagerResponse(e.getMessage()));
    }
    }

    @DeleteMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessagerResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().body(new MessagerResponse("Usuario eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessagerResponse(e.getMessage()));
        }
    }
}