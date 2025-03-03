package com.example.mygreetingapp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    @GetMapping
    public String getGreeting() {
        return "{\"message\": \"Hello from GET\"}";
    }

    @PostMapping
    public String postGreeting() {
        return "{\"message\": \"Hello from POST\"}";
    }

    @PutMapping
    public String putGreeting() {
        return "{\"message\": \"Hello from PUT\"}";
    }

    @DeleteMapping
    public String deleteGreeting() {
        return "{\"message\": \"Hello from DELETE\"}";
    }
}
