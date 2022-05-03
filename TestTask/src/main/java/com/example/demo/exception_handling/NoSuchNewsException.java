package com.example.demo.exception_handling;

public class NoSuchNewsException extends RuntimeException{

    public NoSuchNewsException(String message) {
        super(message);
    }
}
