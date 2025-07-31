package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.ScoringCreationRequest;
import com.udemine.course_manage.entity.Scoring;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.ScoringServiceImps;
import com.udemine.course_manage.service.Services.ScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scoring")
public class ScoringController {
    @Autowired
    private ScoringService scoringService;

    @GetMapping
    ApiResponse<List<Scoring>> getAllScoring() {
        ApiResponse<List<Scoring>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(scoringService.getAllScorings());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Scoring> createScoring(@RequestBody ScoringCreationRequest request) {
        ApiResponse<Scoring> apiResponse = new ApiResponse<>();
        apiResponse.setResult(scoringService.createScoring(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Scoring> updateScoring(@PathVariable int id, @RequestBody ScoringCreationRequest request) {
        ApiResponse<Scoring> apiResponse = new ApiResponse<>();
        apiResponse.setResult(scoringService.updateScoring(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteScoring(@PathVariable int id) {
        scoringService.deleteScoring(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
