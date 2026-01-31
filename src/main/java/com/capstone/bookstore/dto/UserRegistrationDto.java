package com.capstone.bookstore.dto;

// Used when client sends registration data
public record UserRegistrationDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        String address
) {}
