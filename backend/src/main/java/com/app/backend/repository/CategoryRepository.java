package com.app.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.model.Category;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
   Optional<Category> findByName(String name);
    boolean existsByName(String name); 

}
