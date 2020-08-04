package com.electronics.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason ="There is no such cart which exists")
public class NoSuchCartExist extends Exception{
    public NoSuchCartExist(String message) {
        super(message);
    }
}
