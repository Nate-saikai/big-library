package com.capstone.bookstore.services;

import com.capstone.bookstore.dto.OrderRequestDto;
import com.capstone.bookstore.models.*;
import com.capstone.bookstore.repositories.*;
import com.capstone.bookstore.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookOrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderCartRepository orderCartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final BookSearchService bookSearchService;

    public BookOrderService(OrderRepository orderRepository, BookRepository bookRepository, OrderCartRepository orderCartRepository, UserRepository userRepository, CartItemRepository cartItemRepository, BookSearchService bookSearchService) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.orderCartRepository = orderCartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookSearchService = bookSearchService;
    }

    public OrderRequestDto addBookToOrderCart(Long bookId, int quantityToAdd, String token) throws RuntimeException {

        if (!JwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token!");
        }

        String username = JwtUtil.extractUsername(token);

        User orderUser = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        OrderCart cart = orderCartRepository.findOrderCartByUser(orderUser);

        // Check if this book is already in the order cart
        Optional<CartItem> existing = cart.getCartItems().stream()
                .filter(ob -> ob.getBook().getBookId().equals(bookId))
                .findFirst();

        if (existing.isPresent()) {
            // Increase quantity
            CartItem bookItem = existing.get();
            bookItem.setQuantity(bookItem.getQuantity() + quantityToAdd);
        } else {
            // Add new line item
            CartItem bookItem = new CartItem();
            bookItem.setBook(book);
            bookItem.setQuantity(quantityToAdd);
            cart.getCartItems().add(bookItem);
            orderCartRepository.save(cart);
        }

        return new OrderRequestDto(cart.getCartItems().getLast().getBook(), LocalDateTime.now());
    }

    public Book removeBookFromOrderCart(Long bookId, String token) {

        if (!JwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token!");
        }

        String username = JwtUtil.extractUsername(token);

        User orderUser = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OrderCart cart = orderCartRepository.findOrderCartByUser(orderUser);

        // Check if this book is in the order cart
        Optional<CartItem> existing = cart.getCartItems().stream()
                .filter(ob -> ob.getBook().getBookId().equals(bookId))
                .findFirst();

        if(existing.isPresent()) {
            Book bookToBeRemoved = bookSearchService.getBookById(bookId);
            cartItemRepository.removeCartItemByBook(bookToBeRemoved);
            return bookToBeRemoved;
        }
        else {
            return null;
        }

    }

}
