package de.lbeck.tipdabottle.customer.exception;

import de.lbeck.tipdabottle.common.exception.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Order(1)
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now()
        ),  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEMailAlreadyExistsException(EmailAlreadyExistsException e){
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                Instant.now()
        ), HttpStatus.BAD_REQUEST);
    }
}
