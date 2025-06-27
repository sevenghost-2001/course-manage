package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.ReviewCreationRequest;
import com.udemine.course_manage.entity.Review;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    ApiResponse<List<Review>>getAllReviews(){
        ApiResponse<List<Review>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewService.getAllReviews());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Review> createReview(@RequestBody ReviewCreationRequest request){
        ApiResponse<Review> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewService.createReview(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Review> updateReview(@PathVariable int id,@RequestBody ReviewCreationRequest request){
        ApiResponse<Review> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewService.updateReview(id,request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteReview(@PathVariable int id){
        reviewService.deleteReview(id);
        
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
