package com.sap.subsystem.common.error.handler;

import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.subsystem.common.error.ApiError;
import com.sap.subsystem.common.error.exception.EntityNotFoundException;
import com.sap.subsystem.common.error.exception.DuplicateEntityException;
import com.sap.subsystem.common.error.exception.GithubException;
import com.sap.subsystem.common.error.exception.InvalidSecretException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleEntityNotFoundException(final EntityNotFoundException exception){
        log.error("Entity not found: {}", exception);
        return new ApiError(exception.getMessage());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateEntityException(final DuplicateEntityException exception){
        log.error("Duplicate entity: {}", exception);
        return new ApiError(exception.getMessage());
    }

    @ExceptionHandler(GithubException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiError handleGithubException(final GithubException exception){
        log.error("Github request exception: {}", exception);
        return new ApiError(exception.getMessage());
    }

    @ExceptionHandler(InvalidSecretException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiError handleInvalidSecretException(final InvalidSecretException exception){
        log.error("Invalid secret exception: {}", exception);
        return new ApiError(exception.getMessage());
    }


    @ExceptionHandler(FeignException.class)
    public ApiError handleFeignClientException(final FeignException exception, final HttpServletResponse response){
        response.setStatus(exception.status());
        final String errorMessage = exception.contentUTF8();
        log.error(errorMessage);
        log.error("Feign Client Exception: ", exception);

        try{
            return objectMapper.readValue(errorMessage, new TypeReference<>() {});
        }catch (final JsonProcessingException ex){
            return new ApiError("Something went wrong: %s".formatted(ex.getMessage()));
        }
    }
}
