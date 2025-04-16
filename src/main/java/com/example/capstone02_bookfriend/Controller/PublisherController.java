package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Publisher;
import com.example.capstone02_bookfriend.Service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping("/get")
    public ResponseEntity getAll() {
        return ResponseEntity.status(200).body(publisherService.getAllPublishers());
    }

    @PostMapping("/add")
    public ResponseEntity addPublisher(@RequestBody @Valid Publisher publisher, Errors errors) {
        publisherService.addPublisher(publisher);
        return ResponseEntity.status(200).body(new ApiResponse("new publisher is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePublisher(@PathVariable Integer id, @RequestBody @Valid Publisher publisher, Errors errors) {
        publisherService.updatePublisher(id, publisher);
        return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePublisher(@PathVariable Integer id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }

    //endpoint 6
    @PutMapping("/add-stock/{user_id},{book_id},{stock}")
    public ResponseEntity addStock(@PathVariable Integer user_id, @PathVariable Integer book_id, @PathVariable Integer stock) {
        publisherService.addStock(user_id, book_id, stock);
        return ResponseEntity.status(200).body(new ApiResponse("book stocked done!!"));
    }

    // endpoint 7
    @PutMapping("/checkout-order/{id},{order_id}")
    public ResponseEntity checkoutOrder(@PathVariable Integer id, @PathVariable Integer order_id) {
        publisherService.checkoutOrder(id, order_id);
        return ResponseEntity.status(200).body(new ApiResponse("order is completed"));
    }

    // endpoint 16
    @PutMapping("/add-offer/{id},{percent}")
    public ResponseEntity addOffer(@PathVariable Integer id, @PathVariable Double percent) {
        publisherService.addOffer(id, percent);
        return ResponseEntity.status(200).body(new ApiResponse("new Offer is added"));
    }
}
