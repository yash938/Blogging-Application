package com.BlogApp.serviceImplementation;

import com.BlogApp.dto.AllResponse;
import com.BlogApp.dto.CategoryDto;
import com.BlogApp.entity.Category;
import com.BlogApp.repository.CategoryRepo;
import com.BlogApp.service.CategoryService;
import com.BlogApp.utils.Helper;
import com.BlogApp.utils.PaegableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category createdCategory = categoryRepo.save(category);
        CategoryDto categoryDto1 = modelMapper.map(createdCategory, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryImage(categoryDto.getCategoryImage());
        Category updateCategory = categoryRepo.save(category);
        return modelMapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PaegableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> all = categoryRepo.findAll(pageRequest);
        return Helper.getPaegable(all,CategoryDto.class);
    }

    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepo.delete(category);
    }
}
