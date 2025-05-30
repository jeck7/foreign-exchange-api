package com.example.demo.exception;

public class InternalException extends ApiException {
    public InternalException(String message) {
        super("ERROR_SAVE_DATA", message);
    }
}
