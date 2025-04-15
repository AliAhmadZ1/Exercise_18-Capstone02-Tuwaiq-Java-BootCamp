package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
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

    public void addOrder(Orders orders) {
        Book book = bookRepository.findBooksById(orders.getBook_id());
        User user = userRepository.findUserById(orders.getUser_id());
        if (book == null || user == null)
            throw new ApiException("user or book not found");
        orderRepository.save(orders);
    }

    public void updateOrder(Integer id,Orders orders) {
        Orders oldOrder = orderRepository.findOrderById(id);
        Book book = bookRepository.findBooksById(orders.getBook_id());
        User user = userRepository.findUserById(orders.getUser_id());
        if (book == null || user == null||oldOrder==null)
            throw new ApiException("user, book or order not found");

        oldOrder.setState(orders.getState());
        oldOrder.setUser_id(orders.getUser_id());
        oldOrder.setBook_id(orders.getBook_id());
        oldOrder.setTotal_price(orders.getTotal_price());
        orderRepository.save(oldOrder);

    }

    public void deleteOrder(Integer id) {
        Orders orders = orderRepository.findOrderById(id);
        if (orders==null)
            throw new ApiException("order not found");
        orderRepository.delete(orders);
    }
}
