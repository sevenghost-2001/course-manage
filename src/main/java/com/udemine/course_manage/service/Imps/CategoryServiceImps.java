package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.CategoryCreationRequest;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CategoryRepository;
import com.udemine.course_manage.service.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImps implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryCreationRequest request){
        Category category = new Category();
        if(categoryRepository.existsByname(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Integer id, CategoryCreationRequest request){
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);
        if(existingCategoryOpt.isEmpty()){
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        Category category = existingCategoryOpt.get();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer id){
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("Category not found with by "+id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
