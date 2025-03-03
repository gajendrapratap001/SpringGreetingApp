package com.example.mygreetingapp;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String getGreeting() {
        return "{\"message\": \"Hello World\"}";
    }
}
