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

    private final AuthUserRepository authUserRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthUserRepository authUserRepository,
                                 EmailService emailService,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtil jwtUtil) {
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

    // 🔹 Forgot Password Implementation
    public String forgotPassword(String email, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Sorry! We cannot find the user email: " + email);
        }

        AuthUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);

        emailService.sendEmail(email, "Password Changed", "Your password has been updated successfully.");

        return "Password has been changed successfully!";
    }

    public String resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        AuthUser user = userOptional.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);

        emailService.sendEmail(email, "Password Reset", "Your password has been updated successfully.");

        return "Password reset successfully!";
    }

}