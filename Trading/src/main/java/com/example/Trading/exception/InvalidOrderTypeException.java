package com.example.Trading.exception;

public class InvalidOrderTypeException extends RuntimeException {
    public InvalidOrderTypeException(String message) {
        super(message);
    }
}
