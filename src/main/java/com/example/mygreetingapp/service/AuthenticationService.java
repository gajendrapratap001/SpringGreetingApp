package com.example.mygreetingapp.service;


import com.example.mygreetingapp.dto.AuthUserDTO;
import com.example.mygreetingapp.dto.LoginDTO;
import com.example.mygreetingapp.model.AuthUser;
import com.example.mygreetingapp.repository.AuthUserRepository;
import com.example.mygreetingapp.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthenticationService {

    AuthUserRepository authUserRepository;
    EmailService emailService;
    JwtUtil jwtUtil;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthUserRepository authUserRepository, EmailService emailService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authUserRepository = authUserRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(AuthUserDTO authUserDTO) {
        if (authUserRepository.findByEmail(authUserDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        AuthUser newUser = new AuthUser();
        newUser.setFirstName(authUserDTO.getFirstName());
        newUser.setLastName(authUserDTO.getLastName());
        newUser.setEmail(authUserDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));

        authUserRepository.save(newUser);

        if (emailService != null) {
            emailService.sendEmail(authUserDTO.getEmail(), "Welcome to Greeting App!", "Thank you for registering!");
        } else {
            throw new RuntimeException("Email service is not initialized properly.");
        }

        return "User registered successfully!";
    }


    public Map<String, String> loginUser(LoginDTO loginDTO) {
        Optional<AuthUser> user = authUserRepository.findByEmail(loginDTO.getEmail());

        if (user.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(loginDTO.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("token", token);
            return response;
        }

        throw new RuntimeException("Invalid credentials");
    }
}