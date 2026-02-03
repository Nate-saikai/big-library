package com.capstone.bookstore.services;


import com.capstone.bookstore.dto.UserRegistrationDto;
import com.capstone.bookstore.dto.UserServerResponseDto;
import com.capstone.bookstore.models.User;
import com.capstone.bookstore.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServerResponseDto registerUser(UserRegistrationDto dto) {
        if (userRepository.findUserByUserName(dto.username()).isPresent()) {
            throw new IllegalArgumentException("Username taken!");
        }

        User user = new User();
        user.setUserName(dto.username());
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(dto.password())); // use bean

        User saved = userRepository.save(user);
        return new UserServerResponseDto(saved.getUserId(), saved.getUserName(), saved.getCreatedAt());
    }
}

