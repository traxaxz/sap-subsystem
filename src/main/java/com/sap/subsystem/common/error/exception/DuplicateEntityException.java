package com.sap.subsystem.common.error.exception;

public class DuplicateEntityException extends RuntimeException{
    private static final String RESOURCE_ALREADY_EXISTS = "Resource already exists";

    public DuplicateEntityException(){
        super(RESOURCE_ALREADY_EXISTS);
    }
}
