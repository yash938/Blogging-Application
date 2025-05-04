package com.BlogApp.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private int postId;
    private String title;
    private String content;
    private String imageName;
    private LocalDate postDate;

    private CategoryDto category;
    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();
}
