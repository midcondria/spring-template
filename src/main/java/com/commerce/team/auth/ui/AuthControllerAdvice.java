package com.commerce.team.auth.ui;

import com.commerce.team.global.dto.ApiResponse;
import com.commerce.team.global.exception.RootCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity invalidRequestHandler(MethodArgumentNotValidException e) {
        String[] errorMessages = e.getFieldErrors().stream()
            .map(fieldError -> fieldError.getDefaultMessage())
            .toArray(String[]::new);
        ApiResponse response = ApiResponse.of("입력 값을 확인해주세요.", errorMessages);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response);
    }

    @ExceptionHandler(RootCustomException.class)
    public ResponseEntity rootCustomExceptionHandler(RootCustomException e) {
        ApiResponse response = ApiResponse.of(e.getMessage(), new HashMap<>());
        return ResponseEntity
            .status(e.getStatusCode())
            .body(response);
    }
}
