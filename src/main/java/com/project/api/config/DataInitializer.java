package com.project.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.api.model.Role;
import com.project.api.model.User;
import com.project.api.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.count() == 0) {
            
            User admin = new User();
            admin.setFirstname("Admin");
            admin.setLastname("User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setRole(Role.ADMIN);

            
            User user = new User();
            user.setFirstname("John");
            user.setLastname("Doe");
            user.setEmail("john.doe@example.com");
            user.setPassword(passwordEncoder.encode("userpassword"));
            user.setRole(Role.USER);

            
            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}
