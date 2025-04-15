package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {

    Orders findOrderById(Integer id);

    @Query("select o from Orders o where o.book_id=?1 and o.user_id=?2")
    Orders findOrdersByBook_idAndUser_id(Integer book_id, Integer user_id);

    @Query("select o from Orders o where o.book_id=?1")
    Orders findOrdersByBook_id(Integer book_id);

}
