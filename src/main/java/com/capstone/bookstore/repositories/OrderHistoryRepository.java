package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.OrderHistory;
import com.capstone.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    OrderHistory findOrderHistoryByUser(User user);
}
