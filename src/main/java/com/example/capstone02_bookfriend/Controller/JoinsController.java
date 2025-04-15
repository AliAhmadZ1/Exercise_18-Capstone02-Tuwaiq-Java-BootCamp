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
    public ResponseEntity getAllJoins(){
        if (joinsService.getAllJoins().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no joins"));
        return ResponseEntity.status(200).body(joinsService.getAllJoins());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Joins joins, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = joinsService.addJoin(joins);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new join is added"));
        return ResponseEntity.status(400).body(new ApiResponse("join is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateJoin(@PathVariable Integer id,@RequestBody@Valid Joins joins, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = joinsService.updateJoin(id, joins);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("is already exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        boolean isDeleted = joinsService.deleteJoin(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


}
