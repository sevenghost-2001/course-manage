package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.service.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonsController {
    @Autowired
    private LessonsService lessonsService;
    @GetMapping
    ApiResponse<List<Lessons>>getAllLessons(){
        ApiResponse<List<Lessons>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonsService.getAllLessons());
        return  apiResponse;
    }

}
