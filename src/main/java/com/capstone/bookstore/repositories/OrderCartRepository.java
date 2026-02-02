package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.CartItem;
import com.capstone.bookstore.models.OrderCart;
import com.capstone.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    OrderCart findOrderCartByUser(User user);

    List<OrderCart> searchOrderCartsByOrderHistory_User(User orderHistoryUser);

    Optional<OrderCart> findOrderCartByUserAndStatusIgnoreCase(User user, String status);
}
