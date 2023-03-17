package com.thu.auth.domain.exception;

public class InvalidCredentialsException extends Exception{

    public InvalidCredentialsException(String message){
        super(String.format(message));
    }
}