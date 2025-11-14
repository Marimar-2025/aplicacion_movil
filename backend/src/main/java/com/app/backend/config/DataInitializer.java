package com.app.backend.config;

import com.app.backend.model.User;
import com.app.backend.model.category;
import com.app.backend.repository.UserRepository;
import com.app.backend.repository.categoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.commandLineRunner;
import org.springframeword.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private categoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
   System.out.println("Ejecutando DataInitializer...");

   //Eliniminar y recrear usuarios para asegurar contraseñas correctas 
   if (userRepository.existByUsername("admin")){
    User existngAdmin = userRepository.findByUsername("admin").orElse(null);
    if (existngAdmin != null){
        userRepository.delete(existingAdmin);
        System.out.println("Usuario ADMIN existente eliminado");
    }
    }
     if (userRepository.existByUsername("coordinador")){
    User existngCoord = userRepository.findByUsername("coordinador").orElse(null);
    if (existngCoord != null){
        userRepository.delete(existingCoord);
        System.out.println("Usuario COORDINADOR existente eliminado");
           }
       }
            //Crear usuario ADMIN
       User admin=new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@app.com");
            admin.setRole(User.Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("Usuario ADMIN creado - username: admin, password: admin123 ");

            //Crear usuario COORDINADOR
       User coordinador=new User();
            coordinador.setUsername("coordinador");
            coordinador.setPassword(passwordEncoder.encode("coord123"));
            coordinador.setEmail("coordinador@app.com");
            coordinador.setRole(User.Role.COORDINADOR);
            coordinador.setActive(true);
            userRepository.save(coordinador);
            System.out.println("Usuario COORDINADOR creado - username: coordinador, password: coord123 ");

            System.out.println("DataInitializer completado exitosamente.");
    }
}