package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends JpaRepository<Reading,Integer> {

    Reading findReadById(Integer id);

    @Query("select r from Reading r where r.book_id=?1 and r.user_id=?2")
    Reading findReadingByBook_idAndUser_id(Integer book_id,Integer user_id);

}
