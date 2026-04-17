package com.example.ratelimit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ratelimit.entity.SimpleRateLimiter;

@RestController
@RequestMapping("/api")
public class RateLimitController {
    private final SimpleRateLimiter rateLimiter = new SimpleRateLimiter(5, 60_000);

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        String userId = "user123";
        if (rateLimiter.allowRequest(userId)) {
            return ResponseEntity.ok("Request allowed for user: " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded. Try again later.");
        }
    }
}
