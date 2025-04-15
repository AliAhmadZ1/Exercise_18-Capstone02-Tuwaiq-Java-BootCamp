package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Integer> {

    Publisher findPublisherById(Integer id);

    @Query("select p from Publisher p where p.user_id=?1")
    Publisher findPublisherByUser_id(Integer user_id);
}
