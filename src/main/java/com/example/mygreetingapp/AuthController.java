package com.example.mygreetingapp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody AuthUserDTO authUserDTO) {
        return authenticationService.registerUser(authUserDTO);
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return authenticationService.loginUser(loginDTO);
    }
}