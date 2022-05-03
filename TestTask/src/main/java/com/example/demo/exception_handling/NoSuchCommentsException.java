package com.example.demo.exception_handling;

public class NoSuchCommentsException extends RuntimeException{

    public NoSuchCommentsException(String message) {
        super(message);
    }
}
