package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.CourseIdRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.service.Services.CareerPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-paths")
public class CareerPathController {
    @Autowired
    private CareerPathService careerPathService;
    @PostMapping
    public ApiResponse<List<CareerPathResponse>> getCareerPathsByCourses(@RequestBody CourseIdRequest courseIdRequest) {
        ApiResponse<List<CareerPathResponse>> apiResponse = new ApiResponse<>();
        List<CareerPathResponse> careerPaths = careerPathService.getCareerPathsByCourses(courseIdRequest.getCourseIds());
        apiResponse.setResult(careerPaths);
        return apiResponse;
    }

}
