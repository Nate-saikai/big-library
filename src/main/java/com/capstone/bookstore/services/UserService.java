package com.capstone.bookstore.services;


import com.capstone.bookstore.dto.LoginRequestDto;
import com.capstone.bookstore.dto.LoginResponseDto;
import com.capstone.bookstore.dto.UserRegistrationDto;
import com.capstone.bookstore.dto.UserServerResponseDto;
import com.capstone.bookstore.models.User;
import com.capstone.bookstore.repositories.UserRepository;
import com.capstone.bookstore.security.AuthUtil;
import com.capstone.bookstore.security.PasswordHash;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserServerResponseDto registerUser(UserRegistrationDto dto) {

        if(userRepository.findUserByUserName(dto.username()).isPresent()) {
            throw new IllegalArgumentException("Username taken!");
        }

        // Map DTO to entity
        User user = new User();
        user.setUserName(dto.username());
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash(PasswordHash.hashPassword(dto.password())); // hash password

        User saved = userRepository.save(user); // Map entity back to output DTO

        return new UserServerResponseDto(saved.getUserId(), saved.getUserName(), saved.getCreatedAt());
    }

}
