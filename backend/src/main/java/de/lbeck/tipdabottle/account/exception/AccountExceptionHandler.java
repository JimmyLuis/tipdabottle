package de.lbeck.tipdabottle.account.exception;

import de.lbeck.tipdabottle.common.exception.ErrorResponse;
import de.lbeck.tipdabottle.customer.exception.CustomerNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Order(4)
public class AccountExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now()
        ),  HttpStatus.NOT_FOUND);
    }
}
