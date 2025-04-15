package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.User;
import com.example.capstone02_bookfriend.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        if (userService.getAllUsers().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no users"));
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = userService.addUser(user);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new user is added"));
        return ResponseEntity.status(400).body(new ApiResponse("email is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or email is already used"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    // endpoint 1
    @PutMapping("/join/{id},{group_id}")
    public ResponseEntity joinGroup(@PathVariable Integer id, @PathVariable Integer group_id) {
        boolean isJoined = userService.joinGroup(id, group_id);
        if (isJoined)
            return ResponseEntity.status(200).body(new ApiResponse("you are joined successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("this user or group not found or already joined"));
    }

    // endpoint 2
    @PostMapping("/purchase/{id},{book_id}")
    public ResponseEntity purchaseBook(@PathVariable Integer id, @PathVariable Integer book_id) {
        String purchased = userService.purchaseBook(id, book_id);
        if (purchased.equals("not found"))
            return ResponseEntity.status(400).body(new ApiResponse("user or book not found"));
        if (purchased.equals("price"))
            return ResponseEntity.status(400).body(new ApiResponse("your balance is not enough for this price"));
        if (purchased.equals("stock"))
            return ResponseEntity.status(400).body(new ApiResponse("book is out of stock"));
        return ResponseEntity.status(200).body(new ApiResponse("purchase request is sent"));
    }

    // endpoint 3
    @PutMapping("/return-book/{id},{order_id}")
    public ResponseEntity returnBook(@PathVariable Integer id, @PathVariable Integer order_id) {
        boolean isReturned = userService.returnBook(id, order_id);
        if (isReturned)
            return ResponseEntity.status(200).body(new ApiResponse("your book is return"));
        return ResponseEntity.status(400).body(new ApiResponse("user or order not found or it is not your order or already returned"));
    }

    // endpoint 4
    @PutMapping("/leave/{id},{group_id}")
    public ResponseEntity leaveGroup(@PathVariable Integer id, @PathVariable Integer group_id) {
        boolean isLeaved = userService.leaveGroup(id, group_id);
        if (isLeaved)
            return ResponseEntity.status(200).body(new ApiResponse("Withdrawn is done"));
        return ResponseEntity.status(400).body(new ApiResponse("this user or group not found or already leaved"));
    }

    // endpoint 5
    @PostMapping("/sign-publisher/{id},{type}")
    public ResponseEntity signAsPublisher(@PathVariable Integer id, @PathVariable String type) {
        String publisherSigned = userService.signAsPublisher(id, type);
        if (publisherSigned.equals("price"))
            return ResponseEntity.status(400).body(new ApiResponse("your balance not enough for sign as vip publisher"));
        if (publisherSigned.equals("not found"))
            return ResponseEntity.status(400).body(new ApiResponse("user not found or user is already a publisher"));
        if (publisherSigned.equals("type"))
            return ResponseEntity.status(400).body(new ApiResponse("this type not exist"));
        return ResponseEntity.status(200).body(new ApiResponse("your are as "+type+" publisher"));

    }

    // endpoint 8
    @PostMapping("/add-to-cert/{id},{book_id}")
    public ResponseEntity addToCert(@PathVariable Integer id,@PathVariable Integer book_id){
        boolean isAdded = userService.addToCert(id, book_id);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("book is added to cert"));
        return ResponseEntity.status(400).body(new ApiResponse("user or book nor found or out of stock"));
    }
    // endpoint 9
    @PutMapping("/remove-from-cert/{id},{book_id},{removeAll}")
    public ResponseEntity removeFromCert(@PathVariable Integer id,@PathVariable Integer book_id, @PathVariable Boolean removeAll){
        boolean isRemoved = userService.addToCert(id, book_id);
        if (isRemoved)
            return ResponseEntity.status(200).body(new ApiResponse("is removed from cert"));
        return ResponseEntity.status(400).body(new ApiResponse("cert not found "));
    }

    // endpoint 10
    @PostMapping("/purchase-cert/{id}")
    public ResponseEntity purchaseCert(@PathVariable Integer id) {
        String purchased = userService.purchaseCert(id);
        if (purchased.equals("not found"))
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        if (purchased.equals("empty"))
            return ResponseEntity.status(400).body(new ApiResponse("this cert is empty"));
        if (purchased.equals("stock"))
            return ResponseEntity.status(400).body(new ApiResponse("book is out of stock"));
        if (purchased.equals("price"))
            return ResponseEntity.status(400).body(new ApiResponse("user balance is not enough"));
        return ResponseEntity.status(200).body(new ApiResponse("purchase request is sent"));
    }

    // endpoint 11
    @PutMapping("/deposit/{id},{amount}")
    public ResponseEntity deposit(@PathVariable Integer id,@PathVariable Double amount){
        boolean isDeposited = userService.deposit(id, amount);
        if (isDeposited)
            return ResponseEntity.status(200).body(new ApiResponse("deposited successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }

    // endpoint 12
    @PutMapping("/withdraw-balance/{id},{amount}")
    public ResponseEntity withdrawBalance(@PathVariable Integer id,@PathVariable Double amount){
        boolean isWithdrawn = userService.withdrawBalance(id, amount);
        if (isWithdrawn)
            return ResponseEntity.status(200).body(new ApiResponse("withdrawn balance successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("user not found or balance less than amount"));
    }

    // endpoint 13
    @PostMapping("/read/{id}")
    public ResponseEntity startReading(@PathVariable Integer id){
        boolean isStart = userService.startReading(id);
        if (isStart)
            return ResponseEntity.status(200).body(new ApiResponse("reading is started"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or not joined to group"));
    }

    // endpoint 14
    @PutMapping("/finish-reading/{id},{book_id}")
    public ResponseEntity finishReading(@PathVariable Integer id,@PathVariable Integer book_id){
        boolean isFinished = userService.finishReading(id, book_id);
        if (isFinished)
            return ResponseEntity.status(200).body(new ApiResponse("reading is finished"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or not allowed finish reading at same day of starting"));
    }

}
