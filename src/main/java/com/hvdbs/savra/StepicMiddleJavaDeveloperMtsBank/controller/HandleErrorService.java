package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.controller;

import com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class HandleErrorService {
    @ExceptionHandler
    public ResponseEntity<ApiError> noSuchElementExceptionHandler(NoSuchElementException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage()), NOT_FOUND);
    }
}