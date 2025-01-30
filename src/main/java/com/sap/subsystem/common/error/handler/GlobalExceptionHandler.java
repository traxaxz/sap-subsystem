package com.sap.subsystem.common.error.handler;

import com.sap.subsystem.common.error.ApiResponse;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponse handleEntityNotFoundException(final EntityNotFoundException exception){
        log.error("Entity not found: " + exception);
        return new ApiResponse(exception.getMessage());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ApiResponse handleDuplicateEntityException(final DuplicateEntityException exception){
        log.error("Duplicate entity: " + exception);
        return new ApiResponse(exception.getMessage());
    }
}
