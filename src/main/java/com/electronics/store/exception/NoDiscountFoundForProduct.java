package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="There is no discount found for this Product")
public class NoDiscountFoundForProduct extends  Exception{
    public NoDiscountFoundForProduct(String s) {
    }
}
