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
        return ResponseEntity.status(200).body(groupsService.getAllgroups());
    }

    @PostMapping("/add")
    public ResponseEntity addGroup(@RequestBody@Valid Groups groups, Errors errors){
      groupsService.addGroup(groups);
            return ResponseEntity.status(200).body(new ApiResponse("new group is added"));
     }

    @PutMapping("/update/{id}")
    public ResponseEntity updateGroup(@PathVariable Integer id,@RequestBody@Valid Groups groups, Errors errors){
      groupsService.updateGroup(id, groups);
             return ResponseEntity.status(200).body(new ApiResponse("is updated"));
     }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGroup(@PathVariable Integer id){
      groupsService.deleteGroup(id);
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }

    // endpoint 15
    @PutMapping("/reviewed/{id},{user_id}")
    public ResponseEntity reviewedBook(@PathVariable Integer id, @PathVariable Integer user_id){
      groupsService.reviewedBook(id, user_id);
         return ResponseEntity.status(200).body(new ApiResponse("user finish reviewed"));
    }


}
