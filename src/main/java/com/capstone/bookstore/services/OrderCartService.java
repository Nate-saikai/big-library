package com.capstone.bookstore.services;

import com.capstone.bookstore.models.OrderCart;
import com.capstone.bookstore.models.User;
import com.capstone.bookstore.repositories.OrderCartRepository;
import com.capstone.bookstore.repositories.UserRepository;
import com.capstone.bookstore.security.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderCartService {
    private final UserRepository userRepository;
    private final OrderCartRepository orderCartRepository;

    public OrderCart getUserActiveCart() {

        if (!AuthUtil.isAuthenticated()) {
            throw new RuntimeException("Invalid Session!");
        }

        String username = AuthUtil.extractUsername();

        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OrderCart cart = orderCartRepository.findOrderCartByUserAndStatusIgnoreCase(user, "ACTIVE")
                .orElseThrow(() -> new IllegalStateException("No active cart"));

        return cart;
    }

    public List<OrderCart> getUserOrderHistory(String token) {

        if (!AuthUtil.isAuthenticated()) {
            throw new RuntimeException("Invalid Session!");
        }

        String username = AuthUtil.extractUsername();

        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return orderCartRepository.searchOrderCartsByOrderHistory_User(user);
    }
}
