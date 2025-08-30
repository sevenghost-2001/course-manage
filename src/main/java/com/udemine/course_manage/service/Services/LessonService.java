package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonsCreatonResponse;
import com.udemine.course_manage.entity.Lessons;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonService {
    List<Lessons> getAllLessons();
    Lessons createLessons(LessonsCreatonRequest request);
    Lessons updateLessons(int id, LessonsCreatonRequest request);
    void deleteLesson(int id);
}
