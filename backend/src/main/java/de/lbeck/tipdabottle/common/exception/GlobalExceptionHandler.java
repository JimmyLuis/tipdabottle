package de.lbeck.tipdabottle.common.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Arrays;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(
                500,
                "DEVELOPMENT ONLY: " +ex.getMessage(),
                Instant.now()
        ),  HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                400,
                ex.getMessage(),
                Instant.now()
        ),  HttpStatus.BAD_REQUEST);
    }



}
