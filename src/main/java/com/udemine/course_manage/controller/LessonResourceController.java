package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.entity.LessonResource;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.LessonResourceServiceImps;
import com.udemine.course_manage.service.Services.LessonResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-resources")
public class LessonResourceController {
    @Autowired
    private LessonResourceService lessonResourceService;

    @GetMapping
    ApiResponse<List<LessonResource>> getAllLessonResources() {
        ApiResponse<List<LessonResource>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResourceService.getAllLessonResources());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<LessonResource> createLessonResource(@RequestBody LessonResourceCreationRequest request) {
        ApiResponse<LessonResource> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResourceService.createLessonResource(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<LessonResource> updateLessonResource(@PathVariable int id, @RequestBody LessonResourceCreationRequest request) {
        ApiResponse<LessonResource> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResourceService.updateLessonResource(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteLessonResource(@PathVariable int id) {
        lessonResourceService.deleteLessonResource(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
