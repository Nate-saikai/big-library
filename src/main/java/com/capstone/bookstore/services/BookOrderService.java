package com.capstone.bookstore.services;

import com.capstone.bookstore.dto.OrderDto;
import com.capstone.bookstore.models.*;
import com.capstone.bookstore.repositories.*;
import com.capstone.bookstore.security.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BookOrderService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final BookRepository bookRepository;
    private final OrderCartRepository orderCartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final BookSearchService bookSearchService;
    private final CategoryRepository categoryRepository;

    /**
     *
     * @param orderHistoryRepository
     * @param bookRepository
     * @param orderCartRepository
     * @param userRepository
     * @param cartItemRepository
     * @param bookSearchService
     */
    public BookOrderService(OrderHistoryRepository orderHistoryRepository, BookRepository bookRepository, OrderCartRepository orderCartRepository, UserRepository userRepository, CartItemRepository cartItemRepository, BookSearchService bookSearchService, CategoryRepository categoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.bookRepository = bookRepository;
        this.orderCartRepository = orderCartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookSearchService = bookSearchService;
        this.categoryRepository = categoryRepository;
    }

    /**
     *
     * @param bookId - value for the book id, is unique {@link Long}
     * @param quantityToAdd - value to add {@code int}
     * @return {@link OrderDto} -- pointing to latest added book, regardless if incremented or not
     * @throws RuntimeException - failed to add a book due to (usually) missing
     */
    public OrderDto addBookToOrderCart(Long bookId, int quantityToAdd) throws RuntimeException {

        if (!AuthUtil.isAuthenticated()) {
            throw new RuntimeException("Invalid Session!");
        }

        String username = AuthUtil.extractUsername();

        User orderUser = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        OrderCart cart = orderCartRepository.findOrderCartByUserAndStatusIgnoreCase(orderUser, "ACTIVE")
                .orElseGet(() -> {
                    OrderCart newCart = new OrderCart();
                    newCart.setStatus("ACTIVE");
                    newCart.setUser(orderUser);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setOrderHistory(orderHistoryRepository.findOrderHistoryByUser(orderUser));
                    return orderCartRepository.save(newCart);
                });


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
            bookItem.setCart(cart);
            cart.getCartItems().add(bookItem);
            orderCartRepository.save(cart);
        }


        return new OrderDto(cart.getCartItems().getLast().getBook(), LocalDateTime.now());
    }

    /**
     *
     * @param bookId
     * @return {@link Book} if successful, {@code null} if not found
     */
    public Book removeBookFromOrderCart(Long bookId) {

        if (!AuthUtil.isAuthenticated()) {
            throw new RuntimeException("Invalid Session!");
        }

        String username = AuthUtil.extractUsername();

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
            cart.getCartItems().remove(existing.get()); // remove from parent list
            orderCartRepository.save(cart); // so that hibernate sees the orphan removal
            return bookToBeRemoved;
        }
        else {
            return null;
        }

    }

    /**
     *
     * @return {@link List} of {@link CartItem} upon checkout, for viewing
     */
    public List<CartItem> checkout() {

        if (!AuthUtil.isAuthenticated()) {
            throw new RuntimeException("Invalid Session!");
        }

        String username = AuthUtil.extractUsername();

        User orderUser = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OrderCart cart = orderCartRepository.findOrderCartByUserAndStatusIgnoreCase(orderUser, "ACTIVE")
                .orElseThrow(() -> new IllegalStateException("No active cart"));

        cart.setStatus("CHECKED_OUT");

        return cart.getCartItems();
    }

}
