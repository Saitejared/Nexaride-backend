package com.nexaride.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexaride.api.config.JwtUtil;
import com.nexaride.api.model.Role;
import com.nexaride.api.model.User;
import com.nexaride.api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository repo,
                       PasswordEncoder encoder,
                       JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {

        System.out.println("PASSWORD = " + user.getPassword());

        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() == null) user.setRole(Role.USER);

        return repo.save(user);
    }

    public String login(String email, String password) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}