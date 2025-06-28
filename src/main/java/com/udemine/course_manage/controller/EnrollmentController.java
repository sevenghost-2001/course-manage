package com.udemine.course_manage.controller;


import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.Enrollment;
import com.udemine.course_manage.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    ApiResponse<List<Enrollment>> getAllEnrollments() {
        ApiResponse<List<Enrollment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(enrollmentService.getAllEnrollments());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Enrollment> createEnrollment(@RequestBody EnrollmentCreationRequest request) {
        ApiResponse<Enrollment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(enrollmentService.createEnrollment(request));
        return apiResponse;
    }


}
