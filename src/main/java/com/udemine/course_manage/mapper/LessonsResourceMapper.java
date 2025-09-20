package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.response.LessonsResourceCreationResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.LessonsResource;
import com.udemine.course_manage.service.Services.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonsResourceMapper {
    @Autowired
    FileStorageService FileStorageService;

    public LessonsResourceCreationResponse toLessonsResourceCreationResponse(LessonsResource lessonsResource) {
        LessonsResourceCreationResponse response = new LessonsResourceCreationResponse();
        response.setTitle(lessonsResource.getTitle());
        response.setFileUrl(lessonsResource.getFileUrl());
        response.setId_lesson(lessonsResource.getLesson().getId());
        return response;
    }
}
