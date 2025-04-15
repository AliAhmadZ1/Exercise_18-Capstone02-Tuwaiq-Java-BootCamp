package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.Model.Book;
import com.example.capstone02_bookfriend.Model.Orders;
import com.example.capstone02_bookfriend.Model.User;
import com.example.capstone02_bookfriend.Repository.BookRepository;
import com.example.capstone02_bookfriend.Repository.OrderRepository;
import com.example.capstone02_bookfriend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Boolean addOrder(Orders orders) {
        Book book = bookRepository.findBooksById(orders.getBook_id());
        User user = userRepository.findUserById(orders.getUser_id());
        if (book == null || user == null)
            return false;
        orderRepository.save(orders);
        return true;
    }

    public Boolean updateOrder(Integer id,Orders orders) {
        Orders oldOrder = orderRepository.findOrderById(id);
        Book book = bookRepository.findBooksById(orders.getBook_id());
        User user = userRepository.findUserById(orders.getUser_id());
        if (book == null || user == null||oldOrder==null)
            return false;

        oldOrder.setState(orders.getState());
        oldOrder.setUser_id(orders.getUser_id());
        oldOrder.setBook_id(orders.getBook_id());
        oldOrder.setTotal_price(orders.getTotal_price());
        orderRepository.save(oldOrder);
        return true;

    }

    public Boolean deleteOrder(Integer id) {
        Orders orders = orderRepository.findOrderById(id);
        if (orders==null)
            return false;
        orderRepository.delete(orders);
        return true;
    }
}
