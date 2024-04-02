package com.commerce.team.global.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RootCustomException{

    private static final String MESSAGE = "이미 존재하는 유저입니다.";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
