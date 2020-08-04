package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="Not a valid discount type")
public class InvalidDiscountTypeException extends Exception {
    public InvalidDiscountTypeException(String message) {
        super(message);
    }
}
