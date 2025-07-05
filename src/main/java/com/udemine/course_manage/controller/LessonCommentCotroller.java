package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LessonCommentCreationRequest;
import com.udemine.course_manage.entity.LessonComment;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.LessonCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class LessonCommentCotroller {

    @Autowired
    private LessonCommentService lessonCommentService;

    @GetMapping
    ApiResponse<List<LessonComment>> getAllLessonComments() {
        ApiResponse<List<LessonComment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonCommentService.getAllLessonComments());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<LessonComment> createLessonComment(@RequestBody LessonCommentCreationRequest request) {
        ApiResponse<LessonComment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonCommentService.createLessonComment(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<LessonComment> updateLessonComment(@PathVariable int id, @RequestBody LessonCommentCreationRequest request) {
        ApiResponse<LessonComment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonCommentService.updateLessonComment(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteLessonComment(@PathVariable int id) {
        lessonCommentService.deleteLessonComment(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

}
