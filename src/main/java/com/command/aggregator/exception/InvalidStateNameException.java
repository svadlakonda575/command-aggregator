package com.command.aggregator.exception;

public class InvalidStateNameException extends RuntimeException {

    public InvalidStateNameException(String message){
        super(message);
    }
}
