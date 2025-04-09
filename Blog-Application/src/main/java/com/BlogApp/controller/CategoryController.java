package com.BlogApp.controller;

import com.BlogApp.dto.AllResponse;
import com.BlogApp.dto.CategoryDto;
import com.BlogApp.dto.UserDto;
import com.BlogApp.service.CategoryService;
import com.BlogApp.utils.PaegableResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable int id,@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto updatedcategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedcategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> singleCategory(@PathVariable int id){
        CategoryDto categoryById = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryById);
    }

    @GetMapping
    public ResponseEntity<PaegableResponse<CategoryDto>> allCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir
    ){
        PaegableResponse<CategoryDto> allCategories = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allCategories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AllResponse> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        AllResponse categoryDeletedSuccessfully = AllResponse.builder().message("Category deleted successfully").status(HttpStatus.OK).date(LocalDate.now()).build();
        return ResponseEntity.ok(categoryDeletedSuccessfully);
    }
}
