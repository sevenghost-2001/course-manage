package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.CareerPathRequest;
import com.udemine.course_manage.dto.request.CourseIdRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.entity.CareerPath;
import com.udemine.course_manage.service.Services.CareerPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-paths")
public class CareerPathController {
    @Autowired
    private CareerPathService careerPathService;
    @GetMapping
    public ApiResponse<List<CareerPathResponse>> getCareerPathsByCourses(@RequestBody CourseIdRequest courseIdRequest) {
        ApiResponse<List<CareerPathResponse>> apiResponse = new ApiResponse<>();
        List<CareerPathResponse> careerPaths = careerPathService.getCareerPathsByCourses(courseIdRequest.getCourseIds());
        apiResponse.setResult(careerPaths);
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<CareerPathResponse> createCareerPath(@RequestBody CareerPathRequest request){
        ApiResponse<CareerPathResponse> apiResponse = new ApiResponse<>();
        CareerPathResponse careerPathResponse = careerPathService.createCareerPath(request);
        apiResponse.setResult(careerPathResponse);
        return apiResponse;
    }

    //Chỉnh sửa lộ trình
    @PutMapping("/{id}")
    public ApiResponse<CareerPathResponse> updateCareerPath(@PathVariable int id, @RequestBody CareerPathRequest request) {
        ApiResponse<CareerPathResponse> apiResponse = new ApiResponse<>();
        CareerPathResponse careerPathResponse = careerPathService.updateCareerPath(id,request);
        apiResponse.setResult(careerPathResponse);
        return apiResponse;
    }
    //Lấy ra lô trình theo id

    // Xoá lộ trình theo id

}
