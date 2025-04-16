package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Reading;
import com.example.capstone02_bookfriend.Service.ReadingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/read")
public class ReadingController {

    private final ReadingService readingService;

    @GetMapping("/get")
    public ResponseEntity getAllReadings(){
           return ResponseEntity.status(200).body(readingService.getAllReadings());
    }

    @PostMapping("/add")
    public ResponseEntity addReading(@RequestBody @Valid Reading reading){
       readingService.addReading(reading);
            return ResponseEntity.status(200).body(new ApiResponse("new reading is added"));
   }

    @PutMapping("/update/{id}")
    public ResponseEntity updateReading(@PathVariable Integer id,@RequestBody@Valid Reading reading){
     readingService.updateReading(id, reading);
             return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReading(@PathVariable Integer id){
        readingService.deleteReading(id);
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
     }


}
