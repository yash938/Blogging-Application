package com.BlogApp.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllExceptionHandler {
    private String message;
    private HttpStatus status;
    private LocalDate date;
    private boolean success;
}
