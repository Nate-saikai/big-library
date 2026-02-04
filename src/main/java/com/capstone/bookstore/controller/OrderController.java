package com.capstone.bookstore.controller;

import com.capstone.bookstore.dto.OrderDto;
import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.CartItem;
import com.capstone.bookstore.models.OrderCart;
import com.capstone.bookstore.repositories.CartItemRepository;
import com.capstone.bookstore.security.AuthUtil;
import com.capstone.bookstore.services.BookOrderService;
import com.capstone.bookstore.services.OrderCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getCurrentCart() {

        OrderCart cart = orderCartService.getUserActiveCart();

        List<Book> bookList = cart.getCartItems().stream().map(CartItem::getBook).toList();

        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addBookToOrderCart(@RequestBody Long bookId) {

        OrderDto dto = bookOrderService.addBookToOrderCart(bookId, 1);

        return ResponseEntity.ok().body(dto);

    }

    @DeleteMapping("/deleteItem")
    public String removeBookFromOrderCart(@RequestBody Long bookId) {

        Book removedBook = bookOrderService.removeBookFromOrderCart(bookId);

        if(removedBook == null) {
            return "This book does not exist";
        }

        return removedBook.getBookName();
    }
}
