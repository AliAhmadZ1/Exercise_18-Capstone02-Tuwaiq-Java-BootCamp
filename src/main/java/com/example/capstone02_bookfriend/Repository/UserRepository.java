package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Publisher;
import com.example.capstone02_bookfriend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    List<User> findUserByRole(String role);

    @Query("select u from User u where u.role='admin' and u.id=?1")
    User checkAdminRole(Integer id);

    User findUserByEmail(String email);

    @Query("select p from Publisher p where p.id=?1")
    Publisher findPublisherById(Integer publisher_id);
}
