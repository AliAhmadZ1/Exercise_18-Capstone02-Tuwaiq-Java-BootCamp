package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club,Integer> {

    Club findClubById(Integer id);

    Club findClubByName(String name);
}
