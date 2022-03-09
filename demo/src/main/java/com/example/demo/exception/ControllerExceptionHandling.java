package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandling {

    @ExceptionHandler({CustomGlobalExceptionHandler.class})
    public ResponseEntity<?> exceptionHandler(CustomGlobalExceptionHandler e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> exceptionHandler(UserNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
