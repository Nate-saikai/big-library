package com.capstone.bookstore.dto;

public record LoginResponseDto (
        Long userId,
        String username,
        String token
) {}
