package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Orders;
import com.example.capstone02_bookfriend.Service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        if (ordersService.getAllOrders().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no orders"));
        return ResponseEntity.status(200).body(ordersService.getAllOrders());
    }

    @PostMapping("/add")
    public ResponseEntity addOrder(@RequestBody @Valid Orders orders, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = ordersService.addOrder(orders);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new order is added"));
        return ResponseEntity.status(400).body(new ApiResponse("order is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOrder(@PathVariable Integer id,@RequestBody@Valid Orders orders, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = ordersService.updateOrder(id, orders);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("user or book not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable Integer id){
        boolean isDeleted = ordersService.deleteOrder(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


}
