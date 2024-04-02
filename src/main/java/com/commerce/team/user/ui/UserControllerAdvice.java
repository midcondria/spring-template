package com.commerce.team.user.ui;

import com.commerce.team.global.exception.RootCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity invalidRequestHandler(MethodArgumentNotValidException e) {
        String[] errorMessages = e.getFieldErrors().stream()
            .map(fieldError -> fieldError.getDefaultMessage())
            .toArray(String[]::new);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorMessages);
    }

    @ExceptionHandler(RootCustomException.class)
    public ResponseEntity rootCustomExceptionHandler(RootCustomException e) {
        return ResponseEntity
            .status(e.getStatusCode())
            .body(e.getMessage());
    }
}
