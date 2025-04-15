package com.example.capstone02_bookfriend.Service;

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

    public Boolean addReading(Reading reading) {
        User user = userRepository.findUserById(reading.getUser_id());
        Book book = bookRepository.findBooksById(reading.getBook_id());
        Reading existReading = readingRepository.findReadingByBook_idAndUser_id(reading.getBook_id(), reading.getUser_id());
        if (user == null || book == null || existReading != null)
            return false;
        readingRepository.save(reading);
        return true;
    }

    public Boolean updateReading(Integer id, Reading reading) {
        User user = userRepository.findUserById(reading.getUser_id());
        Book book = bookRepository.findBooksById(reading.getBook_id());
        Reading existReading = readingRepository.findReadingByBook_idAndUser_id(reading.getBook_id(), reading.getUser_id());
        Reading oldReading = readingRepository.findReadById(id);
        if (user == null || book == null || existReading != null || oldReading == null)
            return false;

        oldReading.setUser_id(reading.getUser_id());
        oldReading.setState(reading.getState());
        oldReading.setBook_id(reading.getBook_id());
        readingRepository.save(oldReading);
        return true;
    }

    public Boolean deleteReading(Integer id) {
        Reading reading = readingRepository.findReadById(id);
        if (reading == null)
            return false;
        readingRepository.delete(reading);
        return true;
    }
}
