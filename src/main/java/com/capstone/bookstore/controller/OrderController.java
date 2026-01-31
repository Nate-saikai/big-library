package com.capstone.bookstore.controller;

import com.capstone.bookstore.dto.OrderRequestDto;
import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.services.BookOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final BookOrderService bookOrderService;

    public OrderController(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @PostMapping("/newItem")
    public ResponseEntity<?> addBookToOrderCart(@RequestHeader("Authorization") String authHeader, @RequestBody Long bookId) {

        String token = authHeader.replace("Bearer ", "");
        OrderRequestDto dto = bookOrderService.addBookToOrderCart(bookId, 1, token);

        return ResponseEntity.ok().body(dto);

    }

    @DeleteMapping("/deleteItem")
    public String removeBookFromOrderCart(@RequestHeader("Authorization") String authHeader, @RequestBody Long bookId) {

        String token = authHeader.replace("Bearer ", "");
        Book removedBook = bookOrderService.removeBookFromOrderCart(bookId, token);

        if(removedBook == null) {
            return "This book does not exist";
        }

        return removedBook.getBookName();
    }
}
