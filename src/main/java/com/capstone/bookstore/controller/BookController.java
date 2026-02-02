package com.capstone.bookstore.controller;

import com.capstone.bookstore.dto.BookDto;
import com.capstone.bookstore.models.Book;
import com.capstone.bookstore.repositories.CategoryRepository;
import com.capstone.bookstore.services.BookSearchService;
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

        if (library.isEmpty()){
            return ResponseEntity.ok(null);
        }

        List<BookDto> bookDto = library.stream()
                .map(book -> new BookDto(
                        book.getBookId(),
                        book.getBookCode(),
                        book.getCategory(),
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
    public ResponseEntity<List<Book>> getBooksByCategory(@RequestParam("category") String category) {
        if(!categoryRepository.existsByCategoryIgnoreCase(category)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Book> library = bookLibrary.getAllBooksByCategory(category);

        return ResponseEntity.ok().body(library);
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

        return ResponseEntity.ok().body(book.getCategory().getCategory());
    }

}
