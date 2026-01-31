package com.capstone.bookstore.services;

/*
    This interface exists for future-proof purposes, in case
    another book-related class requires the same methods but
    with different implementations
 */

import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.BookCategory;

import java.util.List;

public interface BookSearchFunctions {

    List<Book> getAllBooks();

    Book getBookById(Long bookId);

    Book getBookByAuthor(String author);

    BookCategory getBookCategory(String bookName);

    Book getBookByName(String bookName);

}
