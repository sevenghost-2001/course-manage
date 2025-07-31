package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.RevenueCreationRequest;
import com.udemine.course_manage.entity.Revenue;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.RevenueServiceImps;
import com.udemine.course_manage.service.Services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revenues")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @GetMapping
    ApiResponse<List<Revenue>> getAllRevenues() {
        ApiResponse<List<Revenue>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(revenueService.getAllRevenues());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Revenue> createRevenue(@RequestBody RevenueCreationRequest request) {
        ApiResponse<Revenue> apiResponse = new ApiResponse<>();
        apiResponse.setResult(revenueService.createRevenue(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Revenue> updateRevenue(@PathVariable int id ,@RequestBody RevenueCreationRequest request) {
        ApiResponse<Revenue> apiResponse = new ApiResponse<>();
        apiResponse.setResult(revenueService.updateRevenue(id,request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteRevenue(@PathVariable int id) {
        revenueService.deleteRevenue(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
