package com.wizardcloud.wizardbank.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashingUtil {
    private static final int STRENGTH = 10; // BCrypt work factor
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(STRENGTH);

    private HashingUtil() {
    }

    public static String hashPassword(String rawPassword) {
        if (rawPassword == null) return null;

        return ENCODER.encode(rawPassword);
    }

    public static boolean verifyPassword(String rawPassword, String hashed) {
        if (rawPassword == null || hashed == null) return false;

        return ENCODER.matches(rawPassword, hashed);
    }
}

