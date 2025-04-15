package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Club;
import com.example.capstone02_bookfriend.Service.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/get")
    public ResponseEntity getAllClubs() {
        if (clubService.getAllClubs().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no clubs"));
        return ResponseEntity.status(200).body(clubService.getAllClubs());
    }

    @PostMapping("/add")
    public ResponseEntity addClub(@RequestBody@Valid Club club, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = clubService.addClub(club);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new club is added"));
        return ResponseEntity.status(400).body(new ApiResponse("group or category not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClub(@PathVariable Integer id, @RequestBody@Valid Club club, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = clubService.updateClub(id, club);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or already exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClub(@PathVariable Integer id) {
        boolean isDeleted = clubService.deleteClub(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

}
