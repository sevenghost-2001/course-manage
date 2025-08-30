package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonsResourceCreationResponse;
import com.udemine.course_manage.entity.LessonsResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonResourceService {
    List<LessonsResource> getAllLessonResources();
    LessonsResource createLessonResource(LessonResourceCreationRequest request);
    LessonsResource updateLessonResource(int id, LessonResourceCreationRequest request);
    void deleteLessonResource(int id);
}
