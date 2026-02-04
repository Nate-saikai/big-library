package com.capstone.bookstore.services;

import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSearchService implements BookSearchFunctions {

    private final BookRepository bookRepository;

    public BookSearchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByCategoryName(String categoryName) {

        return bookRepository.searchBooksByCategory_CategoryNameIgnoreCase(categoryName);
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.searchBookByBookId(bookId);
    }

    @Override
    public Book getBookByAuthor(String author) {
        return bookRepository.searchBookByAuthor(author);
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookRepository.searchBookByBookName(bookName);
    }


}
