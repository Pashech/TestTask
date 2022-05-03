package com.example.demo.exception_handling;

public class DuplicateCommentException extends RuntimeException{

    public DuplicateCommentException(String message) {
        super(message);
    }
}
