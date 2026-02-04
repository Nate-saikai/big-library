package com.capstone.bookstore.dto;

import com.capstone.bookstore.models.Book;
import java.time.LocalDateTime;

public record OrderDto(
    Book book,
    LocalDateTime addedOn
) {}
