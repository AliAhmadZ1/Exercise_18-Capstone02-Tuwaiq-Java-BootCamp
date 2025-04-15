package com.example.capstone02_bookfriend.Service;

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

    public Boolean addPublisher(Publisher publisher) {
        Publisher existPublisher = publisherRepository.findPublisherByUser_id(publisher.getUser_id());
        if (existPublisher != null)
            return false;
        publisherRepository.save(publisher);
        return true;
    }

    public Boolean updatePublisher(Integer id, Publisher publisher) {
        Publisher existPublisher = publisherRepository.findPublisherByUser_id(publisher.getUser_id());
        Publisher oldPublisher = publisherRepository.findPublisherById(id);
        if (existPublisher != null || oldPublisher == null)
            return false;

        oldPublisher.setType(publisher.getType());
        oldPublisher.setUser_id(publisher.getUser_id());
        publisherRepository.save(oldPublisher);
        return true;
    }

    public Boolean deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findPublisherById(id);
        if (publisher == null)
            return false;
        publisherRepository.delete(publisher);
        return true;
    }


    // endpoint 6
    public Boolean addStock(Integer user_id,Integer book_id,Integer stock){
        Book book = bookRepository.findBooksById(book_id);
        Publisher publisher = publisherRepository.findPublisherByUser_id(user_id);

        if (book==null||publisher==null||publisher.getId()!=book.getPublisher_id())
            return false;

        book.setStock(book.getStock()+stock);
        bookRepository.save(book);
        return true;
    }

    // endpoint 7
    public Boolean checkoutOrder(Integer id,Integer order_id){
        Publisher publisher = publisherRepository.findPublisherById(id);
        Orders orders = orderRepository.findOrderById(order_id);
        if (orders!=null){
            Book book = bookRepository.findBooksById(orders.getBook_id());
            User user = userRepository.findUserById(publisher.getUser_id());
            if (book==null||user==null)
                return false;
            if (book.getPublisher_id()!=publisher.getId()||orders.getState().equals("completed"))
                return false;
            user.setBalance(orders.getTotal_price()+ user.getBalance());
            orders.setState("completed");
            userRepository.save(user);
            orderRepository.save(orders);
            return true;
        }
        return false;
    }

    // endpoint 16
    public Boolean addOffer(Integer id, Double percent){
        Publisher publisher = publisherRepository.findPublisherById(id);
        if (publisher==null)
            return false;
        List<Book> books = bookRepository.findBooksByPublisher_id(publisher.getId());
        if (books.isEmpty()||percent>100)
            return false;
        for (Book b:books){
            b.setPrice(b.getPrice()-(b.getPrice()*(percent/100)));
            bookRepository.save(b);
        }
        return true;

    }

}
