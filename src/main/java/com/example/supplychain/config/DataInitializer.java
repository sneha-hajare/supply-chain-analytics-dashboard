
package com.example.supplychain.config;

import com.example.supplychain.entity.User;
import com.example.supplychain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (!userRepository.existsByUsername("admin")) {

                User admin = new User();
                admin.setFullName("System Administrator");
                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder.encode("ChangeMe@123"));
                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println(
                        "Initial admin account created. Change its password after login.");
            }
        };
    }
}
