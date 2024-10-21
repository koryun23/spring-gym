package org.example.exception;

//@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CustomIllegalArgumentException extends IllegalArgumentException {

    public CustomIllegalArgumentException(String msg) {
        super(msg);
    }
}
