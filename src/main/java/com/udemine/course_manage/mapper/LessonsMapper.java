package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonsCreatonResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonsMapper {
    @Autowired
    FileStorageService FileStorageService;

    public LessonsCreatonResponse toLessonsCreationResponse(Lessons lessons) {
        LessonsCreatonResponse response = new LessonsCreatonResponse();
        response.setTitle(lessons.getTitle());
        response.setDuration(lessons.getDuration());
        response.setVideo_url(lessons.getVideoUrl());
        response.setWatch_duration(lessons.getWatchDuration());
        response.setId_module(lessons.getModule().getId());

        return response;
    }
}
