package com.dsmhack.igniter.services.exceptions;

public class IgniterException extends Exception {

    public IgniterException(String message) {
        super(message);
    }

    public IgniterException(String message, Throwable cause) {
        super(message, cause);
    }

}
