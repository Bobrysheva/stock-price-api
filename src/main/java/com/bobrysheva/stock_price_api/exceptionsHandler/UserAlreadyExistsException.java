package com.bobrysheva.stock_price_api.exceptionsHandler;


public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
