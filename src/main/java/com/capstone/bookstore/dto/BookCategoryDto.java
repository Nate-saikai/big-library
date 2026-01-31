package com.capstone.bookstore.dto;


public record BookCategoryDto(
        Long bookId,
        String bookName,
        String author,
        String categoryName) {
}
