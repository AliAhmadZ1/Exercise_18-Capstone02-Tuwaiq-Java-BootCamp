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
        return ResponseEntity.status(200).body(clubService.getAllClubs());
    }

    @PostMapping("/add")
    public ResponseEntity addClub(@RequestBody @Valid Club club, Errors errors) {
        clubService.addClub(club);
        return ResponseEntity.status(200).body(new ApiResponse("new club is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClub(@PathVariable Integer id, @RequestBody @Valid Club club, Errors errors) {
        clubService.updateClub(id, club);
        return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClub(@PathVariable Integer id) {
        clubService.deleteClub(id);
        return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }

}
