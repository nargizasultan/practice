package com.example.practice.exceptions.handler;

import com.example.practice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());

    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse alreadyExistsException(AlreadyExistsException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT, e.getClass().getSimpleName(), e.getMessage());

    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequestException(BadRequestException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());

    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse badCredentialException(BadCredentialException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getSimpleName(), e.getMessage());

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse badCredentialException(MethodArgumentNotValidException e) {
        return new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE, e.getClass().getSimpleName(), e.getMessage());

    }

}
