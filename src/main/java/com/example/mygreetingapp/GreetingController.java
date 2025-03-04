package com.example.mygreetingapp;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PostMapping
    public String postGreeting() {
        return "{\"message\": \"Hello from POST\"}";
    }

    @PutMapping("/{id}")
    public Greeting updateGreeting(@PathVariable Long id, @RequestParam String message) {
        return greetingService.updateGreeting(id, message);
    }

    @DeleteMapping
    public String deleteGreeting() {
        return "{\"message\": \"Hello from DELETE\"}";
    }

    @GetMapping
    public Greeting getGreeting(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName) {
        return greetingService.saveGreeting(firstName, lastName);
    }

    @GetMapping("/{id}")
    public Greeting getGreetingById(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }

    @GetMapping("/all")
    public List<Greeting> getAllGreetings() {
        return greetingService.getAllGreetings();
    }
}
