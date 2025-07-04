package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.SubmissionCreationRequest;
import com.udemine.course_manage.entity.Submission;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    ApiResponse<List<Submission>> getAllSubmissions() {
        ApiResponse<List<Submission>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(submissionService.getAllSubmissions());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Submission> createSubmission(@RequestBody SubmissionCreationRequest request) {
        ApiResponse<Submission> apiResponse = new ApiResponse<>();
        apiResponse.setResult(submissionService.createSubmission(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Submission> updateSubmission(@PathVariable int id, @RequestBody SubmissionCreationRequest request) {
        ApiResponse<Submission> apiResponse = new ApiResponse<>();
        apiResponse.setResult(submissionService.updateSubmission(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteSubmission(@PathVariable int id) {
        submissionService.deleteSubmission(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
