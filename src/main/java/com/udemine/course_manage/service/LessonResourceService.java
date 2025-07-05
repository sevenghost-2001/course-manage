package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.entity.LessonResource;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.LessonResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonResourceService {
    @Autowired
    private LessonResourceRepository lessonResourceRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public List<LessonResource> getAllLessonResources() {
        return lessonResourceRepository.findAll();
    }

    public LessonResource createLessonResource(LessonResourceCreationRequest request) {
        LessonResource lessonResource = new LessonResource();
        lessonResource.setTitle(request.getTitle());
        lessonResource.setFileUrl(request.getFileUrl());

        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonResource.setLesson(lesson);
        return lessonResourceRepository.save(lessonResource);
    }

    public LessonResource updateLessonResource(int id, LessonResourceCreationRequest request) {
        LessonResource lessonResource = lessonResourceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_RESOURCE_NOT_FOUND));
        lessonResource.setTitle(request.getTitle());
        lessonResource.setFileUrl(request.getFileUrl());

        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonResource.setLesson(lesson);
        return lessonResourceRepository.save(lessonResource);
    }

    public void deleteLessonResource(int id) {
        if (!lessonResourceRepository.existsById(id)) {
            throw new AppException(ErrorCode.LESSON_RESOURCE_NOT_FOUND);
        }
        lessonResourceRepository.deleteById(id);
    }
}
