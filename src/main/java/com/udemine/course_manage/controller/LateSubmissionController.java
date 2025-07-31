package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LateSubmissionCreationRequest;
import com.udemine.course_manage.entity.LateSubmission;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.LateSubmissionServiceImps;
import com.udemine.course_manage.service.Services.LateSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/late-submissions")
public class LateSubmissionController {
    @Autowired
    private LateSubmissionService lateSubmissionService;

    @GetMapping
    ApiResponse<List<LateSubmission>> getAllLateSubmissions() {
        ApiResponse<List<LateSubmission>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lateSubmissionService.getAllLateSubmissions());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<LateSubmission> createLateSubmission(@RequestBody LateSubmissionCreationRequest request) {
        ApiResponse<LateSubmission> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lateSubmissionService.createLateSubmission(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<LateSubmission> updateLateSubmission(@PathVariable int id, @RequestBody LateSubmissionCreationRequest request) {
        ApiResponse<LateSubmission> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lateSubmissionService.updateLateSubmission(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteLateSubmission(@PathVariable int id) {
        lateSubmissionService.deleteLateSubmission(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
