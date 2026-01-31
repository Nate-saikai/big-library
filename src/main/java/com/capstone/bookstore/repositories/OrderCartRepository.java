package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.CartItem;
import com.capstone.bookstore.models.OrderCart;
import com.capstone.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    OrderCart findOrderCartByUser(User user);
}
