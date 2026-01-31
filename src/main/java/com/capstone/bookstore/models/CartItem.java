package com.capstone.bookstore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CartItemTable")
public class CartItem {

    @Id
    @GeneratedValue
    private Long cartItemId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private OrderCart cart;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
}
