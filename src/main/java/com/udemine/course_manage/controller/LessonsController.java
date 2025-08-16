package com.udemine.course_manage.controller;
import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.LessonServiceImps;
import com.udemine.course_manage.service.Services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonsController {
    @Autowired
    private LessonService lessonsService;
    @GetMapping
    ApiResponse<List<Lessons>> getAllLessons(){
        ApiResponse<List<Lessons>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonsService.getAllLessons());
        return  apiResponse;
    }

    @PostMapping
    ApiResponse<Lessons> createLessons(@RequestBody LessonsCreatonRequest request){
        ApiResponse<Lessons> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonsService.createLessons(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Lessons> updateLessons(@PathVariable int id,@RequestBody LessonsCreatonRequest request){
        ApiResponse<Lessons> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonsService.updateLessons(id,request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteLessons(@PathVariable int id){
        lessonsService.deleteLesson(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

}
