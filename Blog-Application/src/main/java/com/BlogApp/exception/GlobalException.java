package com.BlogApp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AllExceptionHandler> UserNotFoundException(RuntimeException ex){
        log.info("Exception occurred: {}", ex.getMessage());
        AllExceptionHandler resourceNotFound = AllExceptionHandler.builder().message("Resource not found").date(LocalDate.now()).status(HttpStatus.NOT_FOUND).success(false).build();
        return ResponseEntity.ok(resourceNotFound);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> MethodArgumentException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.ok(errorMap);
    }
}
