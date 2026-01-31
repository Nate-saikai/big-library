package com.capstone.bookstore.services;

import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.BookCategory;
import com.capstone.bookstore.repositories.BookRepository;
import com.capstone.bookstore.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSearchService implements BookSearchFunctions {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookSearchService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByCategory(String category){
        return bookRepository.findBooksByCategory(
                categoryRepository.getBookCategoryByCategory(category)
        );
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
    public BookCategory getBookCategory(String bookName) {
        Book book = bookRepository.searchBookByBookName(bookName);

        return book.getCategory();
    }

    @Override
    public Book getBookByName(String bookName){
        return bookRepository.searchBookByBookName(bookName);
    }


}
