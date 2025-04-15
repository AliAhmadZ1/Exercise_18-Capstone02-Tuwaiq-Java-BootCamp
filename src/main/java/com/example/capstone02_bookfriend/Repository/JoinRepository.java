package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Joins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRepository extends JpaRepository<Joins,Integer> {

    Joins findJoinById(Integer id);

    @Query("select j from Joins j where j.group_id=?1")
    Joins findJoinsByGroup_id(Integer group_id);

    @Query("select j from Joins j where j.user_id=?1")
    Joins findJoinsByUser_id(Integer user_id);

    @Query("select j from Joins j where j.group_id=?1 and j.user_id=?2")
    Joins findJoinsByGroup_idAndUser_id(Integer group_id,Integer user_id);

}
