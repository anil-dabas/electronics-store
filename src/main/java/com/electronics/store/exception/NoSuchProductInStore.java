package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="There is no such Product in the store exception")
public class NoSuchProductInStore extends Exception{
    public NoSuchProductInStore(String s) {
        super(s);
    }
}
