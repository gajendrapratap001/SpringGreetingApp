package com.example.mygreetingapp;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GreetingService {
    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public Greeting saveGreeting(String firstName, String lastName) {
        String message;
        if (firstName != null && lastName != null) {
            message = "Hello " + firstName + " " + lastName;
        } else if (firstName != null) {
            message = "Hello " + firstName;
        } else if (lastName != null) {
            message = "Hello " + lastName;
        } else {
            message = "Hello World";
        }

        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }

    public Greeting getGreetingById(Long id) {
        return greetingRepository.findById(id).orElse(null);
    }

    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }

    public Greeting updateGreeting(Long id, String newMessage) {
        return greetingRepository.findById(id)
                .map(greeting -> {
                    greeting.setMessage(newMessage);
                    return greetingRepository.save(greeting);
                }).orElse(null);
    }

    public boolean deleteGreeting(Long id) {
        if (greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
