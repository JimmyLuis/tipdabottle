package de.lbeck.tipdabottle.purchase.exception;

import de.lbeck.tipdabottle.common.exception.ErrorResponse;
import de.lbeck.tipdabottle.customer.exception.CustomerNotFoundException;
import de.lbeck.tipdabottle.customer.exception.EmailAlreadyExistsException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Order(3)
class PurchaseExceptionHandler {

    @ExceptionHandler(PurchaseDeniedException.class)
    public ResponseEntity<ErrorResponse> handlePurchaseDeniedException(PurchaseDeniedException e) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now()
        ),  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePurchaseNotFoundException(PurchaseNotFoundException e){
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                Instant.now()
        ), HttpStatus.BAD_REQUEST);
    }
}

