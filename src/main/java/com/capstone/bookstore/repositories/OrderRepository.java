package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderHistory, Long> {
}
