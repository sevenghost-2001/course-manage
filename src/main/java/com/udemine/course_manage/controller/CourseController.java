package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    ApiResponse<List<Course>> getAllCourses() {
        ApiResponse<List<Course>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getAllCourses());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Course> createCourse(@RequestBody @Valid CourseCreationRequest request) {
        ApiResponse<Course> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.createCourse(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Course> updateCourse(@PathVariable Integer id, @RequestBody CourseCreationRequest request) {
        ApiResponse<Course> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.updateCourse(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteCourseDiscount(@PathVariable int id) {
        courseService.deleteCourse(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}

