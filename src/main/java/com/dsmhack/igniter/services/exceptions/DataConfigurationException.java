package com.dsmhack.igniter.services.exceptions;

public class DataConfigurationException extends IntegrationException {

    public DataConfigurationException(String message) {
        super(message);
    }

    public DataConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
