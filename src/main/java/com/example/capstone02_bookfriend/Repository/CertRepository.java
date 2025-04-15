package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Cert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertRepository extends JpaRepository<Cert,Integer> {

    Cert findCertById(Integer id);

    @Query("select c from Cert c where c.user_id=?1")
    List<Cert> findCertsByUserId(Integer user_id);

    @Query("select c from Cert c where c.user_id=?1")
    Cert findCertByUserId(Integer user_id);

    @Query("select c from Cert c where c.user_id=?1 and c.book_id=?2")
    Cert findCertByUser_idAndBook_id(Integer user_id,Integer book_id);
}
