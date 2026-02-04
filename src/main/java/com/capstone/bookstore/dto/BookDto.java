package com.capstone.bookstore.dto;

public record BookDto(
        Long bookId,
        String bookCode,
        String category,
        String bookName,
        String author,
        String publish_date,
        Double price,
        String description){
}
