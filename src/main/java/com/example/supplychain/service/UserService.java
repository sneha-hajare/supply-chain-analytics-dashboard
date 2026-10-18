
package com.example.supplychain.service;

import com.example.supplychain.entity.User;
import com.example.supplychain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(
            String fullName,
            String username,
            String rawPassword,
            String role) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }

        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters.");
        }

        if (userRepository.existsByUsername(username.trim())) {
            throw new IllegalArgumentException(
                    "Username already exists.");
        }

        if (role == null ||
                !role.matches("ADMIN|MANAGER|ANALYST")) {
            throw new IllegalArgumentException("Invalid user role.");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username.trim());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);

        return userRepository.save(user);
    }
}
