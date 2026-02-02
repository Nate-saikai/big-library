package com.capstone.bookstore.dto;

// Used when client sends registration data
public record UserRegistrationDto(
        String username,
        String password
) {}
