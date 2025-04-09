package com.BlogApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private int categoryId;
    @NotBlank(message = "Title should not be blank")
    private String categoryTitle;
    @NotBlank(message = "Description should not be blank")
    private String categoryDescription;
    private String categoryImage;

}
