package com.example.ratelimit.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleRateLimiterTest {
    @Test
    void testAllowRequestWithinLimit() {
        SimpleRateLimiter limiter = new SimpleRateLimiter(3, 1000); // 3 requests per second
        String user = "user1";
        assertTrue(limiter.allowRequest(user));
        assertTrue(limiter.allowRequest(user));
        assertTrue(limiter.allowRequest(user));
        // 4th request should be blocked
        assertFalse(limiter.allowRequest(user));
    }

    @Test
    void testAllowRequestAfterWindowReset() throws InterruptedException {
        SimpleRateLimiter limiter = new SimpleRateLimiter(2, 200);
        String user = "user2";
        assertTrue(limiter.allowRequest(user));
        assertTrue(limiter.allowRequest(user));
        assertFalse(limiter.allowRequest(user));
        // Wait for window to reset
        Thread.sleep(250);
        assertTrue(limiter.allowRequest(user));
    }

    @Test
    void testSeparateUsers() {
        SimpleRateLimiter limiter = new SimpleRateLimiter(1, 1000);
        assertTrue(limiter.allowRequest("userA"));
        assertTrue(limiter.allowRequest("userB"));
        assertFalse(limiter.allowRequest("userA"));
        assertFalse(limiter.allowRequest("userB"));
    }
}
