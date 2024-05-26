package com.twitterApplication.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        log.error("An error occurred while performing this operation : {}", e.getMessage());
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(com.minitwitter.exceptions.ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(com.minitwitter.exceptions.ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getFieldValue(),HttpStatus.NOT_FOUND);
    }

}
