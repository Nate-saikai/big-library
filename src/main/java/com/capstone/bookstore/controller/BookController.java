package com.capstone.bookstore.controller;

import com.capstone.bookstore.dto.BookCategoryDto;
import com.capstone.bookstore.dto.BookDto;
import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.models.BookCategory;
import com.capstone.bookstore.repositories.CategoryRepository;
import com.capstone.bookstore.services.BookSearchService;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookSearchService bookLibrary;
    private final CategoryRepository categoryRepository;

    public BookController(BookSearchService bookLibrary, CategoryRepository categoryRepository){
        this.bookLibrary = bookLibrary;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(){

        List<Book> library = bookLibrary.getAllBooks();

        List<BookDto> bookDto = library.stream()
                .map(book -> new BookDto(
                        book.getBookId(),
                        book.getBookCode(),
                        book.getCategory().getCategoryName(),
                        book.getBookName(),
                        book.getAuthor(),
                        book.getPublish_date(),
                        book.getPrice(),
                        book.getDescription()
                ))
                .toList();


        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/bookId")
    public ResponseEntity<Book> getBookById(@RequestParam("id") Long bookId) {

        Book book = bookLibrary.getBookById(bookId);

        if(book == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Book>> getBooksByCategoryName(@RequestParam("category") String categoryName) {

//        try {
//            if (!categoryRepository.existsByCategoryNameIgnoreCase(categoryName)) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//        } catch (PropertyReferenceException e) {
//            System.err.println("Repository method failed: existsByCategoryNameIgnoreCase");
//            e.printStackTrace();
//            return ResponseEntity.ok(List.of());
//        }

        List<Book> library = null;
        try {
            library = bookLibrary.getAllBooksByCategoryName(categoryName);
        } catch (PropertyReferenceException e) {
            System.err.println("Repository method failed: searchBooksByCategory_CategoryNameIgnoreCase");
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(library);
    }

    @GetMapping("/author")
    public ResponseEntity<Book> getBookByAuthor(@RequestParam("author") String author) {
        Book book = bookLibrary.getBookByAuthor(author);

        if(book == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(book);
    }

    @GetMapping("/category/book")
    public ResponseEntity<String> getBookCategory(@RequestParam("name") String bookName) {
        Book book = bookLibrary.getBookByName(bookName);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(book.getCategory().getCategoryName());
    }

    @GetMapping("/category/list")
    public List<BookCategoryDto> getAllCategories() {

        List<BookCategory> bookCategories = categoryRepository.findAll();
        List<BookCategoryDto> bookCategoryDtos = bookCategories.stream()
                .map(bookCategory -> new BookCategoryDto(
                        bookCategory.getCategory_id(),
                        bookCategory.getCategoryName()
                ))
                .toList();

        return bookCategoryDtos;
    }

}
