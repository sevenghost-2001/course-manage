package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.ReviewRequestCreationRequest;
import com.udemine.course_manage.entity.ReviewRequest;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.ReviewRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-requests")
public class ReviewRequestController {
    @Autowired
    private ReviewRequestService reviewRequestService;

    @GetMapping
    ApiResponse<List<ReviewRequest>> getAllReviewRequests() {
        ApiResponse<List<ReviewRequest>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewRequestService.getAllReviewRequests());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<ReviewRequest> createReviewRequest(@RequestBody ReviewRequestCreationRequest request) {
        ApiResponse<ReviewRequest> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewRequestService.createReviewRequest(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<ReviewRequest> updateReviewRequest(@PathVariable int id, @RequestBody ReviewRequestCreationRequest request) {
        ApiResponse<ReviewRequest> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewRequestService.updateReviewRequest(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteReviewRequest(@PathVariable int id) {
        reviewRequestService.deleteReviewRequest(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
