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
        if (readingService.getAllReadings().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no readings"));
        return ResponseEntity.status(200).body(readingService.getAllReadings());
    }

    @PostMapping("/add")
    public ResponseEntity addReading(@RequestBody @Valid Reading reading, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = readingService.addReading(reading);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new reading is added"));
        return ResponseEntity.status(400).body(new ApiResponse("reading is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateReading(@PathVariable Integer id,@RequestBody@Valid Reading reading, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = readingService.updateReading(id, reading);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("user, book or reading not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReading(@PathVariable Integer id){
        boolean isDeleted = readingService.deleteReading(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


}
