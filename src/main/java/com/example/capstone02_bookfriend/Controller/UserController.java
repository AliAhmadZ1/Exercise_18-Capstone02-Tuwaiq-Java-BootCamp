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
         return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user) {
         userService.addUser(user);
            return ResponseEntity.status(200).body(new ApiResponse("new user is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user) {
         userService.updateUser(id, user);
             return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
       userService.deleteUser(id);
             return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }

    // endpoint 1
    @PutMapping("/join/{id},{group_id}")
    public ResponseEntity joinGroup(@PathVariable Integer id, @PathVariable Integer group_id) {
       userService.joinGroup(id, group_id);
           return ResponseEntity.status(200).body(new ApiResponse("you are joined successfully"));
   }

    // endpoint 2
    @PostMapping("/purchase/{id},{book_id}")
    public ResponseEntity purchaseBook(@PathVariable Integer id, @PathVariable Integer book_id) {
       userService.purchaseBook(id, book_id);
         return ResponseEntity.status(200).body(new ApiResponse("purchase request is sent"));
    }

    // endpoint 3
    @PutMapping("/return-book/{id},{order_id}")
    public ResponseEntity returnBook(@PathVariable Integer id, @PathVariable Integer order_id) {
      userService.returnBook(id, order_id);
            return ResponseEntity.status(200).body(new ApiResponse("your book is return"));
     }

    // endpoint 4
    @PutMapping("/leave/{id},{group_id}")
    public ResponseEntity leaveGroup(@PathVariable Integer id, @PathVariable Integer group_id) {
      userService.leaveGroup(id, group_id);
             return ResponseEntity.status(200).body(new ApiResponse("Withdrawn is done"));
    }

    // endpoint 5
    @PostMapping("/sign-publisher/{id},{type}")
    public ResponseEntity signAsPublisher(@PathVariable Integer id, @PathVariable String type) {
      userService.signAsPublisher(id, type);
          return ResponseEntity.status(200).body(new ApiResponse("your are as "+type+" publisher"));
    }

    // endpoint 8
    @PostMapping("/add-to-cert/{id},{book_id}")
    public ResponseEntity addToCert(@PathVariable Integer id,@PathVariable Integer book_id){
      userService.addToCert(id, book_id);
             return ResponseEntity.status(200).body(new ApiResponse("book is added to cert"));
     }
    // endpoint 9
    @PutMapping("/remove-from-cert/{id},{book_id},{removeAll}")
    public ResponseEntity removeFromCert(@PathVariable Integer id,@PathVariable Integer book_id, @PathVariable Boolean removeAll){
       userService.addToCert(id, book_id);
             return ResponseEntity.status(200).body(new ApiResponse("is removed from cert"));
    }

    // endpoint 10
    @PostMapping("/purchase-cert/{id}")
    public ResponseEntity purchaseCert(@PathVariable Integer id) {
      userService.purchaseCert(id);
         return ResponseEntity.status(200).body(new ApiResponse("purchase request is sent"));
    }

    // endpoint 11
    @PutMapping("/deposit/{id},{amount}")
    public ResponseEntity deposit(@PathVariable Integer id,@PathVariable Double amount){
        userService.deposit(id, amount);
            return ResponseEntity.status(200).body(new ApiResponse("deposited successfully"));
    }

    // endpoint 12
    @PutMapping("/withdraw-balance/{id},{amount}")
    public ResponseEntity withdrawBalance(@PathVariable Integer id,@PathVariable Double amount){
       userService.withdrawBalance(id, amount);
            return ResponseEntity.status(200).body(new ApiResponse("withdrawn balance successfully"));
     }

    // endpoint 13
    @PostMapping("/read/{id}")
    public ResponseEntity startReading(@PathVariable Integer id){
      userService.startReading(id);
              return ResponseEntity.status(200).body(new ApiResponse("reading is started"));
     }

    // endpoint 14
    @PutMapping("/finish-reading/{id},{book_id}")
    public ResponseEntity finishReading(@PathVariable Integer id,@PathVariable Integer book_id){
       userService.finishReading(id, book_id);
             return ResponseEntity.status(200).body(new ApiResponse("reading is finished"));
    }

}
