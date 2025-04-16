package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Book;
import com.example.capstone02_bookfriend.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/get")
    public ResponseEntity getAllBooks() {
//        if (bookService.getAllBooks().isEmpty())
//            return ResponseEntity.status(400).body(new ApiResponse("there are no books"));
        return ResponseEntity.status(200).body(bookService.getAllBooks());
    }

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody @Valid Book book) {
        bookService.addBook(book);
        return ResponseEntity.status(200).body(new ApiResponse("new book is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBook(@PathVariable Integer id, @RequestBody @Valid Book book) {
        bookService.updateBook(id, book);
        return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBooK(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }


}
