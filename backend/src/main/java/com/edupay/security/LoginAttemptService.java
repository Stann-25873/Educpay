package com.edupay.security;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate limiting & account lockout service.
 * - Max 5 failed attempts per email before lockout
 * - Lockout duration: 15 minutes
 * - Thread-safe via ConcurrentHashMap
 */
@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration LOCKOUT_DURATION = Duration.ofMinutes(15);

    private final ConcurrentHashMap<String, AttemptInfo> attempts = new ConcurrentHashMap<>();

    /**
     * Check if the given email is currently locked out.
     */
    public boolean isLocked(String email) {
        AttemptInfo info = attempts.get(email.toLowerCase().trim());
        if (info == null) return false;
        if (info.attemptCount >= MAX_ATTEMPTS) {
            if (Duration.between(info.lastAttempt, Instant.now()).compareTo(LOCKOUT_DURATION) > 0) {
                // Lockout expired, reset
                attempts.remove(email.toLowerCase().trim());
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Register a failed login attempt. Increments counter, resets lockout timer.
     */
    public void registerFailedAttempt(String email) {
        String key = email.toLowerCase().trim();
        attempts.compute(key, (k, existing) -> {
            if (existing == null) {
                return new AttemptInfo(1, Instant.now());
            }
            return new AttemptInfo(existing.attemptCount + 1, Instant.now());
        });
    }

    /**
     * Reset failed attempts on successful login.
     */
    public void reset(String email) {
        attempts.remove(email.toLowerCase().trim());
    }

    public int getRemainingAttempts(String email) {
        AttemptInfo info = attempts.get(email.toLowerCase().trim());
        if (info == null) return MAX_ATTEMPTS;
        return Math.max(0, MAX_ATTEMPTS - info.attemptCount);
    }

    private record AttemptInfo(int attemptCount, Instant lastAttempt) {}
}
