package com.commerce.team.order.ui;

import com.commerce.team.global.dto.ApiResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(IamportResponseException.class)
    public ResponseEntity iamportResponseExceptionHandler(IamportResponseException e) {
        ApiResponse response = ApiResponse.of(e.getMessage(), null);
        return ResponseEntity
            .status(e.getHttpStatusCode())
            .body(response);
    }
}
