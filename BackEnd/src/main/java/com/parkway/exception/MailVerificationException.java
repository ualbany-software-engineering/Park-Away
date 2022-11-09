package com.parkway.exception;

public class MailVerificationException extends RuntimeException {
    public MailVerificationException() {
        super();
    }

    public MailVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailVerificationException(String message) {
        super(message);
    }

    public MailVerificationException(Throwable cause) {
        super(cause);
    }
}
