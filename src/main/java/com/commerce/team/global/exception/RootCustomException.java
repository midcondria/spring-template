package com.commerce.team.global.exception;

public abstract class RootCustomException extends RuntimeException{

    public RootCustomException(String message) {
        super(message);
    }

    public RootCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
