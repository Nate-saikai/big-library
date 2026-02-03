package com.capstone.bookstore.controller;

import com.capstone.bookstore.dto.LoginRequestDto;
import com.capstone.bookstore.dto.LoginResponseDto;
import com.capstone.bookstore.dto.UserRegistrationDto;
import com.capstone.bookstore.dto.UserServerResponseDto;
import com.capstone.bookstore.models.OrderCart;
import com.capstone.bookstore.services.OrderCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.capstone.bookstore.services.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final OrderCartService orderCartService;

    public UserController(UserService userService, OrderCartService orderCartService) {
        this.userService = userService;
        this.orderCartService = orderCartService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto dto) {

        try {
            UserServerResponseDto createdUser = userService.registerUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        return ResponseEntity.ok(Map.of(
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        ));
    }

    @GetMapping("/orderHistory")
    public ResponseEntity<?> getUserOrderHistory(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        List<OrderCart> userCart = orderCartService.getUserOrderHistory(token);

        return ResponseEntity.ok(userCart);
    }

}
