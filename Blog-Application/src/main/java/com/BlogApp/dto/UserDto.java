package com.BlogApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    @NotNull(message = "Name should not be null")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Password should not be null")
    private String password;
    @Size(max = 100, message = "About should not exceed 100 characters")
    private String about;
}
