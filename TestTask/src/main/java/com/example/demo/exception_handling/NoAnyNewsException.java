package com.example.demo.exception_handling;

public class NoAnyNewsException extends RuntimeException{

    public NoAnyNewsException(String message) {
        super(message);
    }
}
