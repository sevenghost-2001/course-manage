package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonResponseCreationRequest;
import com.udemine.course_manage.entity.LessonResponse;

import java.util.List;

public interface LessonResponseService {
    List<LessonResponse> getAllLessonResponses();
    LessonResponse createLessonResponse(LessonResponseCreationRequest request);
    LessonResponse updateLessonResponse(int id, LessonResponseCreationRequest request);
    void deleteLessonResponse(int id);
}
