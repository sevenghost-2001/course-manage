package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.entity.LessonsResource;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Services.LessonResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-resources")
public class LessonResourceController {
    @Autowired
    private LessonResourceService lessonResourceService;

    @GetMapping
    ApiResponse<List<LessonsResource>> getAllLessonResources() {
        ApiResponse<List<LessonsResource>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonResourceService.getAllLessonResources());
        return apiResponse;
    }

    @PostMapping()
    public ApiResponse<LessonsResource> createLessonResource(
            @RequestParam("title") String title,
            @RequestParam("fileUrl") MultipartFile fileUrl,
            @RequestParam("id_lesson") int id_lesson) {
        ApiResponse<LessonsResource> apiResponse = new ApiResponse<>();
        LessonResourceCreationRequest request = new LessonResourceCreationRequest();
        request.setTitle(title);
        request.setFileUrl(fileUrl);
        request.setId_lesson(id_lesson);
        apiResponse.setResult(lessonResourceService.createLessonResource(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<LessonsResource> updateLessonResource(@PathVariable int id, @RequestBody LessonResourceCreationRequest request) {
        ApiResponse<LessonsResource> apiResponse = new ApiResponse<>();
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
