package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
import com.example.capstone02_bookfriend.Model.Book;
import com.example.capstone02_bookfriend.Model.Reading;
import com.example.capstone02_bookfriend.Model.User;
import com.example.capstone02_bookfriend.Repository.BookRepository;
import com.example.capstone02_bookfriend.Repository.ReadingRepository;
import com.example.capstone02_bookfriend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<Reading> getAllReadings() {
        return readingRepository.findAll();
    }

    public void addReading(Reading reading) {
        User user = userRepository.findUserById(reading.getUser_id());
        Book book = bookRepository.findBooksById(reading.getBook_id());
        Reading existReading = readingRepository.findReadingByBook_idAndUser_id(reading.getBook_id(), reading.getUser_id());
        if (user == null || book == null || existReading != null)
            throw new ApiException("user or book not found, or is already started");
        readingRepository.save(reading);
    }

    public void updateReading(Integer id, Reading reading) {
        User user = userRepository.findUserById(reading.getUser_id());
        Book book = bookRepository.findBooksById(reading.getBook_id());
        Reading existReading = readingRepository.findReadingByBook_idAndUser_id(reading.getBook_id(), reading.getUser_id());
        Reading oldReading = readingRepository.findReadById(id);
        if (user == null || book == null || existReading != null || oldReading == null)
            throw new ApiException("user or book not found, or is already started");

        oldReading.setUser_id(reading.getUser_id());
        oldReading.setState(reading.getState());
        oldReading.setBook_id(reading.getBook_id());
        readingRepository.save(oldReading);
    }

    public void deleteReading(Integer id) {
        Reading reading = readingRepository.findReadById(id);
        if (reading == null)
            throw new ApiException("reading not found");
        readingRepository.delete(reading);
    }
}
