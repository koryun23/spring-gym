package org.example.exception;

public class CustomIllegalArgumentException extends IllegalArgumentException {

    public CustomIllegalArgumentException(String msg) {
        super(msg);
    }
}
