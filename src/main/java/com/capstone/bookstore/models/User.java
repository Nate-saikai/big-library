package com.capstone.bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @NotBlank
    @Column(name = "passwordHash", nullable = false)
    private String passwordHash;

    // LocalDateTime set upon creation
    // Uses LocalDateTime.now()

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    // useless column right now, needs
    // implementation of change password
    // in services and controller endpoint
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // Comment to remind me that this is
    // NOT A PHYSICAL COLUMN IN THE TABLE
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderHistory> orderHistory;
    

}
