package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="This product is not available in the cart ")
public class ProductNotInCartException extends Exception {
    public ProductNotInCartException(String message) {
        super(message);
    }
}
