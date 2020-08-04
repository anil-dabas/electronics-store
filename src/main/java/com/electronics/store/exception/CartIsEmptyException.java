package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason =" The Cart is empty ")
public class CartIsEmptyException extends Exception {
    public CartIsEmptyException(String message) {
        super(message);
    }
}
