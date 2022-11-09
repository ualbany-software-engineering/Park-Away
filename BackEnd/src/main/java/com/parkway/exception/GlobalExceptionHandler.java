package com.parkway.exception;

import com.parkway.model.APIError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<APIError> validationException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.error("Resource not found exception : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.NOT_FOUND.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Resource not found")
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<APIError> sqlIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex,
            HttpServletRequest request) {

        log.error("SQL Integrity Violated exception : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("SQL Integrity Violated")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<APIError> emptyResultDataAccessException(
            EmptyResultDataAccessException ex,
            HttpServletRequest request) {

        log.error("EmptyResultDataAccessException : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("No Resource found")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<APIError> methodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        log.error("MethodArgumentTypeMismatchException : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Invalid parameters passed")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<APIError> illegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        log.error("IllegalArgumentException : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Invalid details passed")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MailVerificationException.class})
    public ResponseEntity<APIError> mailVerificationException(
            MailVerificationException ex,
            HttpServletRequest request) {

        log.error("MailVerificationException : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Unable to send verification Mail")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BookingNotAvailableException.class})
    public ResponseEntity<APIError> bookingNotAvailableException(
            BookingNotAvailableException ex,
            HttpServletRequest request) {

        log.error("BookingNotAvailableException : " + ex.getLocalizedMessage() +
                " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("No availability for given timeslot")
                        .build(), HttpStatus.NOT_ACCEPTABLE);
    }
}
