package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Groups;
import com.example.capstone02_bookfriend.Service.GroupsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupsController {

    private final GroupsService groupsService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        if (groupsService.getAllgroups().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no groups"));
        return ResponseEntity.status(200).body(groupsService.getAllgroups());
    }

    @PostMapping("/add")
    public ResponseEntity addGroup(@RequestBody@Valid Groups groups, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = groupsService.addGroup(groups);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new group is added"));
        return ResponseEntity.status(400).body(new ApiResponse("group name is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateGroup(@PathVariable Integer id,@RequestBody@Valid Groups groups, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = groupsService.updateGroup(id, groups);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("group name is already exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGroup(@PathVariable Integer id){
        boolean isDeleted = groupsService.deleteGroup(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    // endpoint 15
    @PutMapping("/reviewed/{id},{user_id}")
    public ResponseEntity reviewedBook(@PathVariable Integer id, @PathVariable Integer user_id){
        boolean isReviewed = groupsService.reviewedBook(id, user_id);
        if (isReviewed)
            return ResponseEntity.status(200).body(new ApiResponse("user finish reviewed"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or not finish reading"));
    }


}
