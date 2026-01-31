package com.capstone.bookstore.dto;

import com.capstone.bookstore.models.Book;
import java.time.LocalDateTime;

public record OrderRequestDto(
    Book book,
    LocalDateTime addedOn
) {}
