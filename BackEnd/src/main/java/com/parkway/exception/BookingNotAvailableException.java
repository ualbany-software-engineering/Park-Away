package com.parkway.exception;

public class BookingNotAvailableException extends RuntimeException {
    public BookingNotAvailableException() {
        super();
    }

    public BookingNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingNotAvailableException(String message) {
        super(message);
    }

    public BookingNotAvailableException(Throwable cause) {
        super(cause);
    }
}
