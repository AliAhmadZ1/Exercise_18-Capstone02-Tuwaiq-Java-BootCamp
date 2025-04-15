package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.Model.*;
import com.example.capstone02_bookfriend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final OrderRepository orderRepository;
    private final JoinRepository joinRepository;
    private final CertRepository certRepository;
    private final ReadingRepository readingRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Boolean addUser(User user) {
        User checkEmail = userRepository.findUserByEmail(user.getEmail());
        if (checkEmail != null)
            return false;
        userRepository.save(user);
        return true;
    }

    public Boolean updateUser(Integer id, User user) {
        User checkEmail = userRepository.findUserByEmail(user.getEmail());
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
            return false;
        if (checkEmail!=null) {
            if (!oldUser.getEmail().equals(user.getEmail()))
                return false;
        }

        oldUser.setBalance(user.getBalance());
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
        return true;
    }

    public Boolean deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    // endpoint 1
    public Boolean joinGroup(Integer id, Integer group_id) {
        Groups groups = groupRepository.findGroupById(group_id);
        User user = userRepository.findUserById(id);
        Joins joins = new Joins();
        Joins checkJoin = joinRepository.findJoinsByGroup_idAndUser_id(group_id, id);
        if (groups == null || user == null)
            return false;
        else {
            if (checkJoin != null)
                if (checkJoin.getState().equals("joined"))
                    return false;
            if (groups.getMax_capacity() == groups.getNumber_of_users())
                return false;

        }

        joins.setUser_id(id);
        joins.setGroup_id(group_id);
        joins.setState("joined");
        groups.setNumber_of_users(groups.getNumber_of_users() + 1);
        user.setGroup_id(group_id);
        if (checkJoin != null) {
            checkJoin.setState("joined");
            joinRepository.save(checkJoin);
        } else
            joinRepository.save(joins);
        groupRepository.save(groups);
        userRepository.save(user);
        return true;
    }

    // endpoint 2
    public String purchaseBook(Integer id, Integer book_id) {
        Book book = bookRepository.findBooksById(book_id);
        User user = userRepository.findUserById(id);
        Orders orders = new Orders();

        if (book == null || user == null)
            return "not found";
        double totalPrice = (book.getPrice() * 0.15) + book.getPrice();
        if (user.getBalance() < totalPrice)
            return "price";
        if (book.getStock() == 0)
            return "stock";
        user.setBalance(user.getBalance() - totalPrice);
        book.setStock(book.getStock() - 1);
        orders.setTotal_price(totalPrice);
        orders.setBook_id(book_id);
        orders.setUser_id(id);
        orders.setState("in progress");
        orderRepository.save(orders);
        userRepository.save(user);
        bookRepository.save(book);
        return "purchased";
    }

    // endpoint 3
    public Boolean returnBook(Integer id, Integer order_id) {
        User user = userRepository.findUserById(id);
        Orders orders = orderRepository.findOrderById(order_id);
        if (user == null)
            return false;
        if (orders != null && orders.getUser_id() == user.getId() && !orders.getState().equals("returned")) {
            Book book = bookRepository.findBooksById(orders.getBook_id());
            Publisher getPublisher = userRepository.findPublisherById(book.getPublisher_id());
            User publisher = userRepository.findUserById(getPublisher.getUser_id());
            double price = orders.getTotal_price() - (book.getPrice() * 0.15);
            user.setBalance(user.getBalance() + price);
            book.setStock(book.getStock() + 1);
            orders.setState("returned");
            publisher.setBalance(publisher.getBalance()-price);
            orderRepository.save(orders);
            userRepository.save(user);
            userRepository.save(publisher);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    // endpoint 4
    public Boolean leaveGroup(Integer id, Integer group_id) {
        Groups groups = groupRepository.findGroupById(group_id);
        User user = userRepository.findUserById(id);
        Joins joins = joinRepository.findJoinsByGroup_idAndUser_id(group_id, id);
        if (joins == null || groups == null || user == null || !joins.getState().equals("joined"))
            return false;

        joins.setState("withdrawn");
        groups.setNumber_of_users(groups.getNumber_of_users() - 1);
        user.setGroup_id(null);
        groupRepository.save(groups);
        userRepository.save(user);
        joinRepository.save(joins);
        return true;
    }

    // endpoint 5
    public String signAsPublisher(Integer id, String type) {
        User user = userRepository.findUserById(id);
        Publisher publisher = new Publisher();
        if (user == null || user.getRole().equals("publisher"))
            return "not found";
        publisher.setUser_id(id);
        if (type.equals("normal"))
            publisher.setType("normal");
        else if (type.equals("vip")) {
            publisher.setType("vip");
            if (user.getBalance() >= 10)
                user.setBalance(user.getBalance() - 10);
            else
                return "price";
        } else
            return "type";
        user.setRole("publisher");
        publisherRepository.save(publisher);
        userRepository.save(user);
        return "done";
    }

    // endpoint 8
    public Boolean addToCert(Integer id,Integer book_id){
        User user = userRepository.findUserById(id);
        Book book = bookRepository.findBooksById(book_id);
        Cert cert = new Cert();
        Cert existCert = certRepository.findCertByUser_idAndBook_id(id, book_id);
        if (user==null||book==null)
            return false;
        if (book.getStock()>cert.getAmount()) {
            if (existCert != null) {
                existCert.setAmount(existCert.getAmount() + 1);
                certRepository.save(existCert);
                return true;
            }
            cert.setAmount(1);
            cert.setUser_id(id);
            cert.setBook_id(book_id);
            certRepository.save(cert);
            return true;
        }
        return false;
    }

    // endpoint 9
    public Boolean removeFromCert(Integer id, Integer book_id, Boolean removeAll){
        Cert cert = certRepository.findCertByUser_idAndBook_id(id, book_id);
        if (cert==null)
            return false;
        if (removeAll||cert.getAmount()==1) {
            certRepository.delete(cert);
            return true;
        }

        cert.setAmount(cert.getAmount()-1);
        certRepository.save(cert);
        return true;
    }

    // endpoint 10
    public String purchaseCert(Integer id){
        User user = userRepository.findUserById(id);
        List<Cert> certs = certRepository.findCertsByUserId(id);
        Orders orders = new Orders();

        if (user==null)
            return "not found";
        if (certs.isEmpty())
            return "empty";

        for (Cert c:certs){
            Book book = bookRepository.findBooksById(c.getBook_id());
            if (book.getStock()==0)
                return "stock";

            double totalPrice = ((book.getPrice() * 0.15) + book.getPrice())*c.getAmount();
            book.setStock(book.getStock() - c.getAmount());
            orders.setTotal_price(totalPrice);
            orders.setUser_id(id);
            orders.setBook_id(book.getId());
            orders.setState("in progress");
            orderRepository.save(orders);

            if (user.getBalance() < totalPrice)
                return "price";
            user.setBalance(user.getBalance()-totalPrice);
            userRepository.save(user);
        }
        return "purchased";
    }

    // endpoint 11
    public Boolean deposit(Integer id,Double amount){
        User user = userRepository.findUserById(id);
        if (user==null)
            return false;
        user.setBalance(user.getBalance()+amount);
        userRepository.save(user);
        return true;
    }

    // endpoint 12
    public Boolean withdrawBalance(Integer id,Double amount){
        User user = userRepository.findUserById(id);
        if (user==null)
            return false;
        if (user.getBalance()>=amount) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // endpoint 13
    public Boolean startReading(Integer id){
        User user = userRepository.findUserById(id);
        Reading reading = new Reading();

        if (user==null)
            return false;
        Groups groups = groupRepository.findGroupById(user.getGroup_id());
        if (groups==null)
            return false;
        Joins joins = joinRepository.findJoinsByGroup_idAndUser_id(groups.getId(),id);
        if (joins==null||joins.getState().equals("withdrawn"))
            return false;
        reading.setBook_id(groups.getBook_id());
        reading.setUser_id(id);
        reading.setState("start");
        reading.setReadingStart(LocalDate.now());
        readingRepository.save(reading);
        return true;
    }

    // endpoint 14
    // to sign user reading state as finish reading book at least one day after start reading
    public Boolean finishReading(Integer id, Integer book_id){
        Reading reading = readingRepository.findReadingByBook_idAndUser_id(book_id,id);
        if (reading==null||reading.getState().equals("done"))
            return false;
        if (reading.getReadingStart().isBefore(LocalDate.now())){
            reading.setState("done");
            reading.setReadingFinish(LocalDate.now());
            readingRepository.save(reading);
            return true;
        }
        return false;
    }


}
