package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.ExerciseCreationRequest;
import com.udemine.course_manage.entity.Exercise;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.ExerciseServiceImps;
import com.udemine.course_manage.service.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    ApiResponse<List<Exercise>> getAllExercises() {
        ApiResponse<List<Exercise>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(exerciseService.getAllExercises());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Exercise> createExercise(@RequestBody ExerciseCreationRequest request) {
        ApiResponse<Exercise> apiResponse = new ApiResponse<>();
        apiResponse.setResult(exerciseService.createExercise(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Exercise> updateExercise(@PathVariable int id,@RequestBody ExerciseCreationRequest request) {
        ApiResponse<Exercise> apiResponse = new ApiResponse<>();
        apiResponse.setResult(exerciseService.updateExercise(id,request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteExercise(@PathVariable int id) {
        exerciseService.deleteExercise(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000); // Assuming 1000 is the code for successful deletion
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
