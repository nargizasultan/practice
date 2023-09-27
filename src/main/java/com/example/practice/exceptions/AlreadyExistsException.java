package com.example.practice.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
