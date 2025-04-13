package com.BlogApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;
    private String postTitle;
    private String postContent;
    private String postImage;
    private LocalDate postDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

}
