package com.sap.subsystem.common.error.exception;

public class InvalidSecretException extends RuntimeException {
    private static final String INVALID_SECRET = "Invalid secret";

    public InvalidSecretException(){
        super(INVALID_SECRET);
    }
}
