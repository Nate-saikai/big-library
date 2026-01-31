package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void removeCartItemByBook(Book book);
}
