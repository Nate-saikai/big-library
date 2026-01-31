package com.capstone.bookstore.dto;

import java.time.LocalDateTime;

// Used when server responds (no password included)
public record UserServerResponseDto(
        Long userId,
        String username,
        LocalDateTime createdAt
) {}
