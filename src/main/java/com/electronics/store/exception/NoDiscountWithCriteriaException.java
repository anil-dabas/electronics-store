package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="There i sno discount with given  criteria")
public class NoDiscountWithCriteriaException extends Exception {
    public NoDiscountWithCriteriaException(String message) {
        super(message);
    }
}
