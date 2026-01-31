package com.capstone.bookstore.services;

import com.capstone.bookstore.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCartService {
    private final OrderRepository orderRepository;

    public OrderCartService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
