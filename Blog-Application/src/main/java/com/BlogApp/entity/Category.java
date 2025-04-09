package com.BlogApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    @Column(name = "title", nullable = false, length = 100)
    private String categoryTitle;
    @Column(name = "description", nullable = false, length = 1000)
    private String categoryDescription;
    private String categoryImage;
}
