package com.capstone.bookstore.repositories;

import com.capstone.bookstore.models.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<BookCategory, Long> {

    boolean existsByCategoryNameIgnoreCase(String categoryName);
    
}
