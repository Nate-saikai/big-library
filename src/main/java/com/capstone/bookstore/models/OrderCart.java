package com.capstone.bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderCartTable")
public class OrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @NotBlank
    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, CHECKED_OUT, ABANDONED

    // Foreign key referencing the User
    // that this cart belongs to
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // Foreign key referencing the Order History
    // that this cart belongs to
    @ManyToOne
    private OrderHistory orderHistory;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

}
