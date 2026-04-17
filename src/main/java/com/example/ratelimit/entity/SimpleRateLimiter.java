package com.example.ratelimit.entity;

import java.util.HashMap;
import java.util.Map;

public class SimpleRateLimiter {
    private final int maxRequests;
    private final long windowMillis;
    private final Map<String, Window> windows = new HashMap<>();

    private static class Window {
        long windowStart;
        int requestCount;
    }

    public SimpleRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        Window window = windows.get(key);
        if (window == null) {
            window = new Window();
            window.windowStart = now;
            window.requestCount = 1;
            windows.put(key, window);
            return true;
        }
        if (now - window.windowStart < windowMillis) {
            if (window.requestCount < maxRequests) {
                window.requestCount++;
                return true;
            } else {
                return false;
            }
        } else {
            window.windowStart = now;
            window.requestCount = 1;
            return true;
        }
    }
}
