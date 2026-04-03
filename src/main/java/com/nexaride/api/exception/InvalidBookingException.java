package com.nexaride.api.exception;

public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}
