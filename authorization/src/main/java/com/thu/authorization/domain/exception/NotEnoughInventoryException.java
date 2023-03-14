package com.thu.authorization.domain.exception;

public class NotEnoughInventoryException extends RuntimeException{

    public NotEnoughInventoryException(String message){
        super(String.format(message));
    }
}