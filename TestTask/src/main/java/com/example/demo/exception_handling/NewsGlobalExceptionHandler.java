package com.example.demo.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NewsGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<NewsIncorrectData> handleException(NoAnyNewsException exception){

        NewsIncorrectData data = new NewsIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NewsIncorrectData> handleException(NoAnyCommentsException exception){

        NewsIncorrectData data = new NewsIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NewsIncorrectData> handleException(NoSuchNewsException exception){

        NewsIncorrectData data = new NewsIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NewsIncorrectData> handleException(NoSuchCommentsException exception){

        NewsIncorrectData data = new NewsIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NewsIncorrectData> handleException(Exception exception){

        NewsIncorrectData data = new NewsIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
