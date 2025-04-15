package com.example.capstone02_bookfriend.Repository;

import com.example.capstone02_bookfriend.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    Book findBooksById(Integer id);

    Book findBooksByIsbn(String isbn);

    @Query("select b from Book b where b.publisher_id=?1")
    List<Book> findBooksByPublisher_id(Integer publisher_id);


}
