package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.Model.Book;
import com.example.capstone02_bookfriend.Model.Category;
import com.example.capstone02_bookfriend.Model.Publisher;
import com.example.capstone02_bookfriend.Repository.BookRepository;
import com.example.capstone02_bookfriend.Repository.CategoryRepository;
import com.example.capstone02_bookfriend.Repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Boolean addBook(Book book){
        Book existBook = bookRepository.findBooksByIsbn(book.getIsbn());
        Publisher publisher = publisherRepository.findPublisherById(book.getPublisher_id());
        Category category = categoryRepository.findCategoryById(book.getCategory_id());

        if (existBook!=null||publisher==null||category==null)
            return false;

        Random random = new Random();
        int num = random.nextInt(1000000,9999999);
        int num2 = random.nextInt(100000,999999);
        book.setIsbn(String.valueOf(num)+String.valueOf(num2));
        book.setPublish_date(LocalDate.now());
        bookRepository.save(book);
        return true;
    }

    public Boolean updateBook(Integer id,Book book){
        Book oldBook= bookRepository.findBooksById(id);
        Book existBook = bookRepository.findBooksByIsbn(book.getIsbn());
        Publisher publisher = publisherRepository.findPublisherById(book.getPublisher_id());
        Category category = categoryRepository.findCategoryById(book.getCategory_id());

        if (existBook==null||publisher==null||category==null||oldBook==null)
            return false;
        if (oldBook.getIsbn()!= book.getIsbn()||oldBook.getCategory_id()!= book.getCategory_id()|oldBook.getPublisher_id()!= book.getPublisher_id())
            return false;
        oldBook.setBind(book.getBind());
        oldBook.setIsbn(book.getIsbn());
        oldBook.setPrice(book.getPrice());
        oldBook.setTitle(book.getTitle());
        oldBook.setCategory_id(book.getCategory_id());
        oldBook.setPublisher_id(book.getPublisher_id());
        oldBook.setStock(book.getStock());
        bookRepository.save(oldBook);
        return true;
    }

    public Boolean deleteBook(Integer id){
        Book book = bookRepository.findBooksById(id);
        if (book==null)
            return false;
        bookRepository.delete(book);
        return true;
    }


}
