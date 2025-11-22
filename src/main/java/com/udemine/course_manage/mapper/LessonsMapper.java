package com.udemine.course_manage.mapper;


import com.udemine.course_manage.dto.response.LessonResponse;
import com.udemine.course_manage.dto.response.LessonsResourceCreationResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonsMapper {
    @Autowired
    FileStorageService FileStorageService;

    public LessonResponse toLessonResponse(Lessons lessons) {
        LessonResponse response = new LessonResponse();
        response.setId(lessons.getId());
        response.setTitle(lessons.getTitle());
        response.setNameModule(lessons.getModule().getTitle());
        response.setVideoUrl(lessons.getVideoUrl());
        response.setDuration(lessons.getDuration());
        response.setWatchDuration(lessons.getWatchDuration());
        response.setCompleted_at(lessons.getCompleted_at());
        List<LessonsResourceCreationResponse> resources = lessons.getResources().stream()
                .map(resource -> {
                    LessonsResourceCreationResponse resResponse = new LessonsResourceCreationResponse();
                    resResponse.setId_lesson(resource.getId());
                    resResponse.setFileUrl(resource.getFileUrl());
                    resResponse.setTitle(resource.getTitle());
                    return resResponse;
                })
                .toList();
        response.setResources(resources);
        return response;
    }
}
