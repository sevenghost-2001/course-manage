package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.ExerciseCreationRequest;
import com.udemine.course_manage.entity.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> getAllExercises();
    Exercise createExercise(ExerciseCreationRequest request);
    Exercise updateExercise(int id, ExerciseCreationRequest request);
    void deleteExercise(int id);
}
