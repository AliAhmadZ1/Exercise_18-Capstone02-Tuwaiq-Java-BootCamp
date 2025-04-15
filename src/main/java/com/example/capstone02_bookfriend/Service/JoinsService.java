package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.Model.Groups;
import com.example.capstone02_bookfriend.Model.Joins;
import com.example.capstone02_bookfriend.Model.User;
import com.example.capstone02_bookfriend.Repository.GroupRepository;
import com.example.capstone02_bookfriend.Repository.JoinRepository;
import com.example.capstone02_bookfriend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinsService {

    private final JoinRepository joinRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public List<Joins> getAllJoins(){
        return joinRepository.findAll();
    }

    public Boolean addJoin(Joins joins){
        User user = userRepository.findUserById(joins.getUser_id());
        Groups groups = groupRepository.findGroupById(joins.getGroup_id());
        Joins existJoin = joinRepository.findJoinsByGroup_idAndUser_id(joins.getGroup_id(), joins.getUser_id());
        if (user==null||groups==null||existJoin!=null)
            return false;

        joinRepository.save(joins);
        return true;
    }

    public Boolean updateJoin(Integer id,Joins joins){
        User user = userRepository.findUserById(joins.getUser_id());
        Groups groups = groupRepository.findGroupById(joins.getGroup_id());
        Joins existJoins = joinRepository.findJoinsByGroup_idAndUser_id(joins.getGroup_id(), joins.getUser_id());
        if (user==null||groups==null||existJoins!=null)
            return false;

        Joins oldJoins = joinRepository.findJoinById(id);
        oldJoins.setGroup_id(joins.getGroup_id());
        oldJoins.setState(joins.getState());
        oldJoins.setUser_id(joins.getUser_id());
        joinRepository.save(oldJoins);
        return true;
    }

    public Boolean deleteJoin(Integer id){
        Joins joins = joinRepository.findJoinById(id);

        if (joins==null)
            return false;
        joinRepository.delete(joins);
        return true;
    }
}




