package de.lbeck.tipdabottle.purchase.exception;

public class PurchaseDeniedException extends RuntimeException {
    public PurchaseDeniedException(String message) {
        super(message);
    }
}
