package com.example.exception;

public class MessageConversionException extends RuntimeException {

    public MessageConversionException() {
        super("Message was of a wrong format");
    }

    public MessageConversionException(String text) {
        super(text);
    }
}
