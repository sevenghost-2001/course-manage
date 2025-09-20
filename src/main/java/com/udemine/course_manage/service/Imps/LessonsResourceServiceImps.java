package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonsResourceCreationResponse;
import com.udemine.course_manage.entity.LessonsResource;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.LessonResourceRepository;
import com.udemine.course_manage.service.Services.LessonResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LessonsResourceServiceImps implements LessonResourceService {
    @Autowired
    private LessonResourceRepository lessonResourceRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<LessonsResource> getAllLessonResources() {
        return lessonResourceRepository.findAll();
    }


    @Override
    public LessonsResource createLessonResource(LessonResourceCreationRequest request) {
        LessonsResource lessonResource = new LessonsResource();
        lessonResource.setTitle(request.getTitle());
        lessonResource.setFileUrl(request.getFileUrl().getOriginalFilename());

        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonResource.setLesson(lesson);
        return lessonResourceRepository.save(lessonResource);
    }

    @Override
    public LessonsResource updateLessonResource(int id, LessonResourceCreationRequest request) {
        LessonsResource lessonResource = lessonResourceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_RESOURCE_NOT_FOUND));
        lessonResource.setTitle(request.getTitle());
        lessonResource.setFileUrl(request.getFileUrl().getOriginalFilename());

        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonResource.setLesson(lesson);
        return lessonResourceRepository.save(lessonResource);
    }

    @Override
    public void deleteLessonResource(int id) {
        if (!lessonResourceRepository.existsById(id)) {
            throw new AppException(ErrorCode.LESSON_RESOURCE_NOT_FOUND);
        }
        lessonResourceRepository.deleteById(id);
    }
}
