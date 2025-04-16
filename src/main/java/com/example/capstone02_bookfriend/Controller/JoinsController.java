package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Joins;
import com.example.capstone02_bookfriend.Service.JoinsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class JoinsController {

    private final JoinsService joinsService;

    @GetMapping("/get")
    public ResponseEntity getAllJoins() {
        return ResponseEntity.status(200).body(joinsService.getAllJoins());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Joins joins, Errors errors) {
        joinsService.addJoin(joins);
        return ResponseEntity.status(200).body(new ApiResponse("new join is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateJoin(@PathVariable Integer id, @RequestBody @Valid Joins joins, Errors errors) {
        joinsService.updateJoin(id, joins);
        return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        joinsService.deleteJoin(id);
        return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }


}
