package com.BlogApp.service;

import com.BlogApp.dto.CategoryDto;
import com.BlogApp.utils.PaegableResponse;

public interface CategoryService {
    // Create
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update
    CategoryDto updateCategory(int id, CategoryDto categoryDto);

    // Get by ID
    CategoryDto getCategoryById(int id);

    // Get all categories with pagination
    PaegableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

    // Delete
    void deleteCategory(int id);
}
