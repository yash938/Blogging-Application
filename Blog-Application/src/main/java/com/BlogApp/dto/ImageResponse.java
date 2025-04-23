package com.BlogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus httpStatus;
    private LocalDate localDate;
}
