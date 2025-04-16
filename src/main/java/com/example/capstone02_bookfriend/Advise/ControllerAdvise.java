package com.example.capstone02_bookfriend.Advise;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ControllerAdvise {


    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException apiException){
        String message = apiException.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        String message = methodArgumentNotValidException.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException(NoResourceFoundException noResourceFoundException){
        return ResponseEntity.status(400).body(noResourceFoundException.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
        return ResponseEntity.status(400).body(methodArgumentTypeMismatchException.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException){
        return ResponseEntity.status(400).body(httpMessageNotReadableException.getMessage());
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException){
        return ResponseEntity.status(400).body(httpMediaTypeNotSupportedException.getMessage());
    }

}
