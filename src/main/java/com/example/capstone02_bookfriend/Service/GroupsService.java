package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
import com.example.capstone02_bookfriend.Model.Groups;
import com.example.capstone02_bookfriend.Model.Joins;
import com.example.capstone02_bookfriend.Model.Reading;
import com.example.capstone02_bookfriend.Model.User;
import com.example.capstone02_bookfriend.Repository.GroupRepository;
import com.example.capstone02_bookfriend.Repository.JoinRepository;
import com.example.capstone02_bookfriend.Repository.ReadingRepository;
import com.example.capstone02_bookfriend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupsService {

    private final GroupRepository groupRepository;
    private final ReadingRepository readingRepository;
    private final UserRepository userRepository;
    private final JoinRepository joinRepository;


    public List<Groups> getAllgroups() {
        return groupRepository.findAll();
    }

    public void addGroup(Groups groups) {
        Groups existGroup = groupRepository.findGroupsByName(groups.getName());
        if (existGroup == null) {
            groupRepository.save(groups);
            return;
        }
        throw new ApiException("group name is already used");
    }

    public void updateGroup(Integer id, Groups groups) {
        Groups oldGroup = groupRepository.findGroupById(id);
        Groups existGroup = groupRepository.findGroupsByName(groups.getName());
        if (oldGroup == null || existGroup != null)
            throw new ApiException("not found or already used");

        oldGroup.setName(groups.getName());
        oldGroup.setUser_id(groups.getUser_id());
        oldGroup.setBook_id(groups.getBook_id());
        oldGroup.setMax_capacity(groups.getMax_capacity());
        oldGroup.setNumber_of_users(groups.getNumber_of_users());
        groupRepository.save(oldGroup);

    }

    public void deleteGroup(Integer id) {
        Groups groups = groupRepository.findGroupById(id);
        if (groups == null)
            throw new ApiException("not found");
        groupRepository.delete(groups);

    }

    // endpoint 15
    public void reviewedBook(Integer id, Integer user_id){
        User leader = userRepository.findUserById(id);
        User user = userRepository.findUserById(user_id);
        if (leader==null||user==null)
            throw new ApiException("user or leader not found");
        Groups groups = groupRepository.findGroupsByUser_id(id);
        if (groups==null)
            throw new ApiException("they not members for any group");
        Joins joins = joinRepository.findJoinsByGroup_idAndUser_id(groups.getId(), user_id);
        Reading reading = readingRepository.findReadingByBook_idAndUser_id(groups.getBook_id(), user_id);
        if (joins==null||reading==null)
            throw new ApiException("not joined to group or not finish reading");
        if (reading.getState().equals("done")){
            reading.setState("reviewed");
            readingRepository.save(reading);
            return;
        }
        throw new ApiException("already reviewed");
    }

}
