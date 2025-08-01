package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.ExerciseCreationRequest;
import com.udemine.course_manage.entity.Exercise;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.ExerciseRepository;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.service.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImps implements ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise createExercise(ExerciseCreationRequest request) {
        Exercise exercise = new Exercise();
        exercise.setTitle(request.getTitle());
        exercise.setDescription(request.getDescription());
        exercise.setDeadline(request.getDeadline());
        //Xử lý thuộc tính khóa ngoại
        Lessons lessons = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        exercise.setLesson(lessons);
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise updateExercise(int id, ExerciseCreationRequest request) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EXERCISE_NOT_FOUND));
        exercise.setTitle(request.getTitle());
        exercise.setDescription(request.getDescription());
        exercise.setDeadline(request.getDeadline());
        Lessons lessons = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        exercise.setLesson(lessons);
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(int id) {
        if (!exerciseRepository.existsById(id)) {
            throw new AppException(ErrorCode.EXERCISE_NOT_FOUND);
        }
        exerciseRepository.deleteById(id);
    }
}
