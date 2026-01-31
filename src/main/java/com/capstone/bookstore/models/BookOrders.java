package com.capstone.bookstore.models;

// THIS TABLE IS DEPRECATED DO NOT USE

/*
    This exists as a junction table between Order Table and Book Table
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_orders",
        uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "book_id"}))

@Setter
@Getter
@NoArgsConstructor
public class BookOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderHistory order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private Integer quantity;

    // No Args Constructor provided by Lombok
    // Getters and Setters provided by Lombok
}
