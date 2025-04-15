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
    public ResponseEntity getAll(){
        if (publisherService.getAllPublishers().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no publishers"));
        return ResponseEntity.status(200).body(publisherService.getAllPublishers());
    }

    @PostMapping("/add")
    public ResponseEntity addPublisher(@RequestBody @Valid Publisher publisher, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = publisherService.addPublisher(publisher);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new publisher is added"));
        return ResponseEntity.status(400).body(new ApiResponse("publisher is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePublisher(@PathVariable Integer id,@RequestBody@Valid Publisher publisher, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = publisherService.updatePublisher(id, publisher);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("publisher is already exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePublisher(@PathVariable Integer id){
        boolean isDeleted = publisherService.deletePublisher(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    //endpoint 6
    @PutMapping("/add-stock/{user_id},{book_id},{stock}")
    public ResponseEntity addStock(@PathVariable Integer user_id,@PathVariable Integer book_id,@PathVariable Integer stock){
        boolean isStocked = publisherService.addStock(user_id, book_id, stock);
        if (isStocked)
            return ResponseEntity.status(200).body(new ApiResponse("book stocked done!!"));
        return ResponseEntity.status(400).body(new ApiResponse("book or publisher not found"));
    }

    // endpoint 7
    @PutMapping("/checkout-order/{id},{order_id}")
    public ResponseEntity checkoutOrder(@PathVariable Integer id, @PathVariable Integer order_id){
        boolean isCompleted = publisherService.checkoutOrder(id, order_id);
        if (isCompleted)
            return ResponseEntity.status(200).body(new ApiResponse("order is completed"));
        return ResponseEntity.status(400).body(new ApiResponse("publisher or order not found. Or publisher and order not related or order is already completed"));
    }

    // endpoint 16
    @PutMapping("/add-offer/{id},{percent}")
    public ResponseEntity addOffer(@PathVariable Integer id, @PathVariable Double percent){
        boolean isAdded = publisherService.addOffer(id, percent);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new Offer is added"));
        return ResponseEntity.status(400).body(new ApiResponse("nof found or percent is not applicable"));
    }
}
