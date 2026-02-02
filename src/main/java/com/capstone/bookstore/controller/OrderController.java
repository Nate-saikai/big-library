package com.capstone.bookstore.controller;

import com.capstone.bookstore.dto.OrderRequestDto;
import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.CartItem;
import com.capstone.bookstore.models.OrderCart;
import com.capstone.bookstore.repositories.CartItemRepository;
import com.capstone.bookstore.services.BookOrderService;
import com.capstone.bookstore.services.OrderCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final BookOrderService bookOrderService;
    private final OrderCartService orderCartService;
    private final CartItemRepository cartItemRepository;

    public OrderController(BookOrderService bookOrderService, OrderCartService orderCartService, CartItemRepository cartItemRepository) {
        this.bookOrderService = bookOrderService;
        this.orderCartService = orderCartService;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCurrentCart(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        OrderCart cart = orderCartService.getUserActiveCart(token);

        List<Book> bookList = cart.getCartItems().stream().map(CartItem::getBook).toList();

        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping("/addItem")
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
