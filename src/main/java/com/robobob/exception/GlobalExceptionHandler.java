package com.robobob.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handlerForDatabaseException(final DatabaseException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResponseMsg(e.getMessage());
        errorResponse.setResponseCode("99");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
