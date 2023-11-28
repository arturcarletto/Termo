package com.termo.connection.exception;

public class InvalidAdapterException extends RuntimeException {

    public InvalidAdapterException(String message, ClassCastException exception) {
        super(message, exception);
    }

}
