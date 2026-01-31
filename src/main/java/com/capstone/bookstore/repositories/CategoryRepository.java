package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<BookCategory, Long> {
    boolean existsByCategory(String category);

    boolean existsByCategoryIgnoreCase(String category);

    BookCategory getBookCategoryByCategory(String category);
}
