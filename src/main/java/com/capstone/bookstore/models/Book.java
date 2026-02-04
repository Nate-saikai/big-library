package com.capstone.bookstore.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BookTable")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String bookCode;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    @JsonBackReference
    private BookCategory category;

    @Column(nullable = false, unique = true)
    private String bookName;

    @Column(nullable = false)
    private String author;

    @Column
    private String publish_date;

    @Column(nullable = false)
    private Double price;

    @Column
    private String description;

    // Getters and Setters provided by Lombok

}
