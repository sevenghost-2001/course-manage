package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LessonResponseCreationRequest;
import com.udemine.course_manage.entity.LessonsResponse;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Services.LessonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-responses")
public class LessonResponseController {
    @Autowired
    private LessonResponseService lessonResponseService;

    @GetMapping
    public ApiResponse<List<LessonsResponse>> getAllLessonResponses() {
        ApiResponse<List<LessonsResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResponseService.getAllLessonResponses());
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<LessonsResponse> createLessonResponse(@RequestBody LessonResponseCreationRequest request) {
        ApiResponse<LessonsResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResponseService.createLessonResponse(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<LessonsResponse> updateLessonResponse(@PathVariable int id, @RequestBody LessonResponseCreationRequest request) {
        ApiResponse<LessonsResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResponseService.updateLessonResponse(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteLessonResponse(@PathVariable int id) {
        lessonResponseService.deleteLessonResponse(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}

