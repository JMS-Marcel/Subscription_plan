package com.project.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final Map<Long, String> sessionStore = new HashMap<>();

   public String invalidateSession(Long userId) {
        // Logic to invalidate the session or token
        if (sessionStore.containsKey(userId)) {
            sessionStore.remove(userId);
            return "Session for user with ID " + userId + " has been invalidated";
        } else {
            return "No active session found for user with ID " + userId;
        }
}
}