package com.example.Trading.exception;

public class ConstraintVoilationException extends RuntimeException {
    public ConstraintVoilationException(String message){
        super(message);
    }
}
