package com.app.backend.controller;

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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable Long id, @RequestBody User user) {
      userService.delete(id);
        return ResponseEntity.ok().body(new MessagerResponse("Usuario eliminado exitosamente"));
    }
}