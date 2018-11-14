package com.dsmhack.igniter.services.exceptions;

public class IntegrationException extends IgniterException {

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
