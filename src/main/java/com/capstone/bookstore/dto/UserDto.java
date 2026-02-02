package com.capstone.bookstore.dto;

public record UserDto(
        Long userId,
        String userName,
        String password) {
}
