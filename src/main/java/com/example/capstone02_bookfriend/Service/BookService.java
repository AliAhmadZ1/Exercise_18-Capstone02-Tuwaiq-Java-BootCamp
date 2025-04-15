package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
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

    public void addBook(Book book){
        Book existBook = bookRepository.findBooksByIsbn(book.getIsbn());
        Publisher publisher = publisherRepository.findPublisherById(book.getPublisher_id());
        Category category = categoryRepository.findCategoryById(book.getCategory_id());

        if (existBook!=null)
            throw new ApiException("book is already exist");
        if (publisher==null)
            throw new ApiException("publisher not found");
        if (category==null)
            throw new ApiException("category not found");
        Random random = new Random();
        int num = random.nextInt(1000000,9999999);
        int num2 = random.nextInt(100000,999999);
        book.setIsbn(String.valueOf(num)+String.valueOf(num2));
        book.setPublish_date(LocalDate.now());
        bookRepository.save(book);
    }

    public void updateBook(Integer id,Book book){
        Book oldBook= bookRepository.findBooksById(id);
        Book existBook = bookRepository.findBooksByIsbn(book.getIsbn());
        Publisher publisher = publisherRepository.findPublisherById(book.getPublisher_id());
        Category category = categoryRepository.findCategoryById(book.getCategory_id());

        if (existBook!=null)
            throw new ApiException("book is already exist");
        if (publisher==null)
            throw new ApiException("publisher not found");
        if (category==null)
            throw new ApiException("category not found");
        if (oldBook==null)
            throw new ApiException("ISBN is already assigned");
        if (oldBook.getIsbn()!= book.getIsbn())
            throw new ApiException("ISBN cannot be changed");
        if (oldBook.getCategory_id()!= book.getCategory_id())
            throw new ApiException("Category cannot be changed");
        if (oldBook.getPublisher_id()!= book.getPublisher_id())
            throw new ApiException("this is not publisher's book");
        oldBook.setBind(book.getBind());
        oldBook.setIsbn(book.getIsbn());
        oldBook.setPrice(book.getPrice());
        oldBook.setTitle(book.getTitle());
        oldBook.setCategory_id(book.getCategory_id());
        oldBook.setPublisher_id(book.getPublisher_id());
        oldBook.setStock(book.getStock());
        bookRepository.save(oldBook);
    }

    public void deleteBook(Integer id){
        Book book = bookRepository.findBooksById(id);
        if (book==null)
            throw new ApiException("book not found");
        bookRepository.delete(book);
    }


}
