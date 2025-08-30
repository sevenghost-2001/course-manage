package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.dto.response.HomePageResponse;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CourseMapper;
import com.udemine.course_manage.service.Imps.CourseServiceImps;
import com.udemine.course_manage.service.Services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;

    @GetMapping
    ApiResponse<List<CourseResponse>> getAllCourses() {
        ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getAllCourses());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<CourseResponse> createCourse(@Valid CourseCreationRequest request) {
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        Course course = courseService.createCourse(request);
        CourseResponse courseResponse = courseMapper.toCourseResponse(course);
        apiResponse.setResult(courseResponse);
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<CourseResponse> updateCourse(@PathVariable Integer id, @RequestBody CourseCreationRequest request) {
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        Course course = courseService.updateCourse(id, request);
        CourseResponse courseResponse = courseMapper.toCourseResponse(course);
        apiResponse.setResult(courseResponse);
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

    @GetMapping("/homepage")
    ApiResponse<?> getHomepage(){
        ApiResponse<HomePageResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getHomePageData());
        return apiResponse;
    }

    @GetMapping("detail/{id}")
    ApiResponse<CourseResponse> getCourseDetail(@PathVariable Integer id) {
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        CourseResponse courseResponse = courseService.getCourseById(id);
        apiResponse.setResult(courseResponse);
        return apiResponse;
    }

}

