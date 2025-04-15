package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
import com.example.capstone02_bookfriend.Model.*;
import com.example.capstone02_bookfriend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;


    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public void addPublisher(Publisher publisher) {
        Publisher existPublisher = publisherRepository.findPublisherByUser_id(publisher.getUser_id());
        if (existPublisher != null)
            throw new ApiException("user is already publisher");
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Integer id, Publisher publisher) {
        Publisher existPublisher = publisherRepository.findPublisherByUser_id(publisher.getUser_id());
        Publisher oldPublisher = publisherRepository.findPublisherById(id);
        if (existPublisher != null || oldPublisher == null)
            throw new ApiException("not found");

        oldPublisher.setType(publisher.getType());
        oldPublisher.setUser_id(publisher.getUser_id());
        publisherRepository.save(oldPublisher);
    }

    public void deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findPublisherById(id);
        if (publisher == null)
            throw new ApiException("publisher not found");
        publisherRepository.delete(publisher);
    }


    // endpoint 6
    public void addStock(Integer user_id,Integer book_id,Integer stock){
        Book book = bookRepository.findBooksById(book_id);
        Publisher publisher = publisherRepository.findPublisherByUser_id(user_id);

        if (book==null||publisher==null||publisher.getId()!=book.getPublisher_id())
            throw new ApiException("book or publisher not found, or not related to each other");

        book.setStock(book.getStock()+stock);
        bookRepository.save(book);
    }

    // endpoint 7
    public void checkoutOrder(Integer id,Integer order_id){
        Publisher publisher = publisherRepository.findPublisherById(id);
        Orders orders = orderRepository.findOrderById(order_id);
        if (orders!=null){
            Book book = bookRepository.findBooksById(orders.getBook_id());
            User user = userRepository.findUserById(publisher.getUser_id());
            if (book==null||user==null)
                throw new ApiException("user or book not found");
            if (book.getPublisher_id()!=publisher.getId()||orders.getState().equals("completed"))
                throw new ApiException("");
            user.setBalance(orders.getTotal_price()+ user.getBalance());
            orders.setState("completed");
            userRepository.save(user);
            orderRepository.save(orders);
        }
        throw new ApiException("order not found");
    }

    // endpoint 16
    public void addOffer(Integer id, Double percent){
        Publisher publisher = publisherRepository.findPublisherById(id);
        if (publisher==null)
            throw new ApiException("publisher not found");
        List<Book> books = bookRepository.findBooksByPublisher_id(publisher.getId());
        if (books.isEmpty()||percent>100)
            throw new ApiException("there are no books for this publisher, or percent greater than 100");
        for (Book b:books){
            b.setPrice(b.getPrice()-(b.getPrice()*(percent/100)));
            bookRepository.save(b);
        }

    }

}
