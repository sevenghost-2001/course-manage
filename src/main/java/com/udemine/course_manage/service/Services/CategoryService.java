package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.CategoryCreationRequest;
import com.udemine.course_manage.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryCreationRequest request);
    Category updateCategory(Integer id, CategoryCreationRequest request);
    void deleteCategory(Integer id);
    List<Category> getAllCategories();
}
