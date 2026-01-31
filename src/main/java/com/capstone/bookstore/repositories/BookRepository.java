package com.capstone.bookstore.repositories;

import com.capstone.bookstore.dto.BookCategoryDto;
import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book searchBookByBookId(Long bookId);
    Book searchBookByAuthor(String author);
    Book searchBookByBookName(String bookName);

    @Query("""
        SELECT new com.capstone.bookstore.dto.BookCategoryDto(
            b.bookId,
            b.bookName,
            b.author,
            c.category
        )
        FROM Book b JOIN b.category c
        WHERE LOWER(c.category) = LOWER(:categoryName)
    """)
    List<BookCategoryDto> searchBooksByCategory(@Param("categoryName") BookCategory categoryName);

    List<Book> findBooksByCategory(BookCategory category);
}
