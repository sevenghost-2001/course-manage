package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.entity.LessonResource;

import java.util.List;

public interface LessonResourceService {
    List<LessonResource> getAllLessonResources();
    LessonResource createLessonResource(LessonResourceCreationRequest request);
    LessonResource updateLessonResource(int id, LessonResourceCreationRequest request);
    void deleteLessonResource(int id);
}
