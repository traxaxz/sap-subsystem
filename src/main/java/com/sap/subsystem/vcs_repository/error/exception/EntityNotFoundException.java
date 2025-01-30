package com.sap.subsystem.vcs_repository.error.exception;

public class EntityNotFoundException extends RuntimeException{
    private static final String ENTITY_NOT_FOUND_EXCEPTION = "Resource not found";

    public EntityNotFoundException(){
        super(ENTITY_NOT_FOUND_EXCEPTION);
    }
}
