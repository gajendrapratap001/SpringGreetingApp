package com.example.mygreetingapp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "Endpoints for user authentication")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a user and sends a welcome email")

    public String registerUser(@Valid @RequestBody AuthUserDTO authUserDTO) {
        return authenticationService.registerUser(authUserDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates a user and returns a success message")

    public String loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return authenticationService.loginUser(loginDTO);
    }
}