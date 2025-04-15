package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
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

    public void addUser(User user) {
        User checkEmail = userRepository.findUserByEmail(user.getEmail());
        if (checkEmail != null)
            throw new ApiException("email is already exist");
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User checkEmail = userRepository.findUserByEmail(user.getEmail());
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
            throw new ApiException("user not found");
        if (checkEmail != null) {
            if (!oldUser.getEmail().equals(user.getEmail()))
                throw new ApiException("email is already used");
        }

        oldUser.setBalance(user.getBalance());
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ApiException("user not found or already deleted");
        userRepository.delete(user);
    }

    // endpoint 1
    public void joinGroup(Integer id, Integer group_id) {
        Groups groups = groupRepository.findGroupById(group_id);
        User user = userRepository.findUserById(id);
        Joins joins = new Joins();
        Joins checkJoin = joinRepository.findJoinsByGroup_idAndUser_id(group_id, id);
        if (groups == null || user == null)
            throw new ApiException("user or group not found");
        if (checkJoin != null) {
            if (checkJoin.getState().equals("joined")) {
                throw new ApiException("user is already joined");
            }
            checkJoin.setState("joined");
            joinRepository.save(checkJoin);
            return;
        }
        if (groups.getMax_capacity() == groups.getNumber_of_users())
            throw new ApiException("group is full");

        joins.setUser_id(id);
        joins.setGroup_id(group_id);
        joins.setState("joined");
        groups.setNumber_of_users(groups.getNumber_of_users() + 1);
        user.setGroup_id(group_id);
        joinRepository.save(joins);
        groupRepository.save(groups);
        userRepository.save(user);
    }

    // endpoint 2
    public void purchaseBook(Integer id, Integer book_id) {
        Book book = bookRepository.findBooksById(book_id);
        User user = userRepository.findUserById(id);
        Orders orders = new Orders();

        if (book == null || user == null)
            throw new ApiException("book or user not found");
        double totalPrice = (book.getPrice() * 0.15) + book.getPrice();
        if (user.getBalance() < totalPrice)
            throw new ApiException("balance is less than price");
        if (book.getStock() == 0)
            throw new ApiException("book is out of stock");
        user.setBalance(user.getBalance() - totalPrice);
        book.setStock(book.getStock() - 1);
        orders.setTotal_price(totalPrice);
        orders.setBook_id(book_id);
        orders.setUser_id(id);
        orders.setState("in progress");
        orderRepository.save(orders);
        userRepository.save(user);
        bookRepository.save(book);
    }

    // endpoint 3
    public void returnBook(Integer id, Integer order_id) {
        User user = userRepository.findUserById(id);
        Orders orders = orderRepository.findOrderById(order_id);
        if (user == null)
            throw new ApiException("user not found");
        if (orders != null && orders.getUser_id() == user.getId() && !orders.getState().equals("returned")) {
            Book book = bookRepository.findBooksById(orders.getBook_id());
            Publisher getPublisher = userRepository.findPublisherById(book.getPublisher_id());
            User publisher = userRepository.findUserById(getPublisher.getUser_id());
            double price = orders.getTotal_price() - (book.getPrice() * 0.15);
            user.setBalance(user.getBalance() + price);
            book.setStock(book.getStock() + 1);
            orders.setState("returned");
            publisher.setBalance(publisher.getBalance() - price);
            orderRepository.save(orders);
            userRepository.save(user);
            userRepository.save(publisher);
            bookRepository.save(book);
        }
        throw new ApiException("book is already returned or not related to user");
    }

    // endpoint 4
    public void leaveGroup(Integer id, Integer group_id) {
        Groups groups = groupRepository.findGroupById(group_id);
        User user = userRepository.findUserById(id);
        Joins joins = joinRepository.findJoinsByGroup_idAndUser_id(group_id, id);
        if (joins == null||groups == null || user == null)
            throw new ApiException("user not joins to the group or user and group not found");
        if (!joins.getState().equals("joined"))
            throw new ApiException("user has already leaved");

        joins.setState("withdrawn");
        groups.setNumber_of_users(groups.getNumber_of_users() - 1);
        user.setGroup_id(null);
        groupRepository.save(groups);
        userRepository.save(user);
        joinRepository.save(joins);
    }

    // endpoint 5
    public void signAsPublisher(Integer id, String type) {
        User user = userRepository.findUserById(id);
        Publisher publisher = new Publisher();
        if (user == null || user.getRole().equals("publisher"))
            throw new ApiException("user not found or already signed as a publisher");
        publisher.setUser_id(id);
        if (type.equals("normal"))
            publisher.setType("normal");
        else if (type.equals("vip")) {
            publisher.setType("vip");
            if (user.getBalance() >= 10)
                user.setBalance(user.getBalance() - 10);
            else
                throw new ApiException("user balance not enough");
        } else
            throw new ApiException("this type not correct");
        user.setRole("publisher");
        publisherRepository.save(publisher);
        userRepository.save(user);
    }

    // endpoint 8
    public void addToCert(Integer id, Integer book_id) {
        User user = userRepository.findUserById(id);
        Book book = bookRepository.findBooksById(book_id);
        Cert cert = new Cert();
        Cert existCert = certRepository.findCertByUser_idAndBook_id(id, book_id);
        if (user == null || book == null)
            throw new ApiException("user or book not found");
        if (book.getStock() > existCert.getAmount()) {
            if (existCert != null) {
                existCert.setAmount(existCert.getAmount() + 1);
                certRepository.save(existCert);
                return;
            }
            cert.setAmount(1);
            cert.setUser_id(id);
            cert.setBook_id(book_id);
            certRepository.save(cert);
            return;
        }
        throw new ApiException("book is out of stock");
    }

    // endpoint 9
    public void removeFromCert(Integer id, Integer book_id, Boolean removeAll) {
        Cert cert = certRepository.findCertByUser_idAndBook_id(id, book_id);
        if (cert == null)
            throw new ApiException("cert is empty");
        if (removeAll || cert.getAmount() == 1) {
            certRepository.delete(cert);
        }

        cert.setAmount(cert.getAmount() - 1);
        certRepository.save(cert);
    }

    // endpoint 10
    public void purchaseCert(Integer id) {
        User user = userRepository.findUserById(id);
        List<Cert> certs = certRepository.findCertsByUserId(id);
        Orders orders = new Orders();

        if (user == null)
            throw new ApiException("user not found");
        if (certs.isEmpty())
            throw new ApiException("cert is empty");

        for (Cert c : certs) {
            Book book = bookRepository.findBooksById(c.getBook_id());
            if (book.getStock() == 0)
                throw new ApiException("book is out of stock");

            double totalPrice = ((book.getPrice() * 0.15) + book.getPrice()) * c.getAmount();
            book.setStock(book.getStock() - c.getAmount());
            orders.setTotal_price(totalPrice);
            orders.setUser_id(id);
            orders.setBook_id(book.getId());
            orders.setState("in progress");
            orderRepository.save(orders);

            if (user.getBalance() < totalPrice)
                throw new ApiException("balance is less the total price");
            user.setBalance(user.getBalance() - totalPrice);
            userRepository.save(user);
        }
    }

    // endpoint 11
    public void deposit(Integer id, Double amount) {
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ApiException("user not found");
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    // endpoint 12
    public void withdrawBalance(Integer id, Double amount) {
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ApiException("user not found");
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
        }
        throw new ApiException("user's balance doesn't have this amount");
    }

    // endpoint 13
    public void startReading(Integer id) {
        User user = userRepository.findUserById(id);
        Reading reading = new Reading();

        if (user == null)
            throw new ApiException("user not found");
        Groups groups = groupRepository.findGroupById(user.getGroup_id());
        if (groups == null)
            throw new ApiException("group not found");
        Joins joins = joinRepository.findJoinsByGroup_idAndUser_id(groups.getId(), id);
        if (joins == null || joins.getState().equals("withdrawn"))
            throw new ApiException("user not in group to reading");
        reading.setBook_id(groups.getBook_id());
        reading.setUser_id(id);
        reading.setState("start");
        reading.setReadingStart(LocalDate.now());
        readingRepository.save(reading);
    }

    // endpoint 14
    // to sign user reading state as finish reading book at least one day after start reading
    public void finishReading(Integer id, Integer book_id) {
        Reading reading = readingRepository.findReadingByBook_idAndUser_id(book_id, id);
        if (reading == null || reading.getState().equals("done"))
            throw new ApiException("user haven't start reading yet, or reading already finished");
        if (reading.getReadingStart().isBefore(LocalDate.now())) {
            reading.setState("done");
            reading.setReadingFinish(LocalDate.now());
            readingRepository.save(reading);
        }
        throw new ApiException("you need at least one day past to finish reading");
    }

}
