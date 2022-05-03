package com.example.demo.exception_handling;

public class DuplicateNewsException extends RuntimeException{

    public DuplicateNewsException(String message) {
        super(message);
    }
}
