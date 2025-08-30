package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonResponseCreationRequest;
import com.udemine.course_manage.entity.LessonsResponse;

import java.util.List;

public interface LessonResponseService {
    List<LessonsResponse> getAllLessonResponses();
    LessonsResponse createLessonResponse(LessonResponseCreationRequest request);
    LessonsResponse updateLessonResponse(int id, LessonResponseCreationRequest request);
    void deleteLessonResponse(int id);
}
