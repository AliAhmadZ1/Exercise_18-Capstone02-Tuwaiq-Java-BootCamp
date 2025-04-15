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
    public ResponseEntity getAllBooks(){
        if (bookService.getAllBooks().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no books"));
        return ResponseEntity.status(200).body(bookService.getAllBooks());
    }

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody@Valid Book book, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = bookService.addBook(book);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new book is added"));
        return ResponseEntity.status(400).body(new ApiResponse("publisher or category not found / or book is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBook(@PathVariable Integer id,@RequestBody@Valid Book book,Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = bookService.updateBook(id, book);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("book, publisher or category not found. if you want change ISBN, publisher or category contact support team"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBooK(@PathVariable Integer id){
        boolean isDeleted = bookService.deleteBook(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


}
