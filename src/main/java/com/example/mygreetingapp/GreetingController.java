package com.example.mygreetingapp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // UC1: Different HTTP Methods
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

    // UC2: Greeting with First Name, Last Name, or Default
    @GetMapping
    public Greeting getGreeting(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName) {
        return greetingService.saveGreeting(firstName, lastName);
    }
}
