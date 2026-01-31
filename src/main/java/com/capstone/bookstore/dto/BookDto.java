package com.capstone.bookstore.dto;

import com.capstone.bookstore.models.BookCategory;

public record BookDto(
        Long bookId,
        String bookCode,
        BookCategory category,
        String bookName,
        String author,
        String publish_date,
        Double price,
        String description){
}
