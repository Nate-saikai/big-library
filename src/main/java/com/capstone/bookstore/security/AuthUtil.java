package com.capstone.bookstore.security;


import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static String extractUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName(); // same as JWT subject
        }
        return null;
    }

    public static boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated();
    }
}



