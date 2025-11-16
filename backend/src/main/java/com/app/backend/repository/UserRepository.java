package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
