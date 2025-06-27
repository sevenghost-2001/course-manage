package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.CategoryCreationRequest;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    ApiResponse<List<Category>> getAllCategories(){
        ApiResponse<List<Category>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categoryService.getAllCategories());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Category> createCategory(@RequestBody @Valid CategoryCreationRequest request){
        ApiResponse<Category> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categoryService.createCategory(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Category> updateCategory(@PathVariable Integer id, @RequestBody CategoryCreationRequest request){
        ApiResponse<Category> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categoryService.updateCategory(id,request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
