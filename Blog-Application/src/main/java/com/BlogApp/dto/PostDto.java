package com.BlogApp.dto;

import com.BlogApp.entity.Category;
import com.BlogApp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private int postId;
    private String title;
    private String content;
    private String imageName;
    private LocalDate postDate;
    private CategoryDto category;
    private UserDto user;
}
