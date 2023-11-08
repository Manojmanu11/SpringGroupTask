package com.example.Trading.exceptionhandler;

import com.example.Trading.dto.ErrorDto;
import com.example.Trading.exception.InvalidOrderTypeException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidOrderTypeException.class)
    public ResponseEntity<ErrorDto> handleInvalidOrderTypeException(InvalidOrderTypeException e) {
        ErrorDto errorResponse = new ErrorDto();
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorDto>> handleCustomException(ConstraintViolationException e) {
        log.info("Returning custom exception handler method: handleCustomException");

        List<ErrorDto> errorList = e.getConstraintViolations()
                .stream()
                .map(constraintViolation -> {
                    ErrorDto errorDto = new ErrorDto();
                    errorDto.setErrorMessage(constraintViolation.getMessage());
                    return errorDto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
    }

}

