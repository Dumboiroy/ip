package com.dumboiroy.seeyes.exception;

public class NoMoreCommandsException extends RuntimeException {
    public NoMoreCommandsException() {
    }

    public NoMoreCommandsException(String message) {
        super(message);
    }
}
