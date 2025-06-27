package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.Course_DiscountCreationRequest;
import com.udemine.course_manage.entity.CourseDiscount;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.CourseDiscountService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course_discounts")
public class CourseDiscountController {
    @Autowired
    private CourseDiscountService courseDiscountService;

    @GetMapping
    ApiResponse<List<CourseDiscount>>getAllCourseDiscount(){
        ApiResponse<List<CourseDiscount>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseDiscountService.getAllCourseDiscount());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<CourseDiscount>createCourseDiscount(@RequestBody @Valid Course_DiscountCreationRequest request){
        ApiResponse<CourseDiscount> apiResponse =  new ApiResponse<>();
        apiResponse.setResult(courseDiscountService.createCourseDiscount(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<CourseDiscount>updateCourseDiscount(@PathVariable int id, @RequestBody @Valid Course_DiscountCreationRequest request){
        ApiResponse<CourseDiscount> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseDiscountService.updateCouseDiscount(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String>deleteCourseDiscount(@PathVariable int id){
        courseDiscountService.deleteCourseDiscount(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

}
