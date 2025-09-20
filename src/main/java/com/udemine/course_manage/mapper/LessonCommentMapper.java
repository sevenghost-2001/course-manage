package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.response.LessonsCommentCreationResponse;
import com.udemine.course_manage.entity.LessonsComment;
import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonCommentMapper {
    @Autowired
    FileStorageService fileStorageService;

    public LessonsCommentCreationResponse toLessonCommentCreationResponse(LessonsComment lessonsComment) {
        LessonsCommentCreationResponse response = new LessonsCommentCreationResponse();
        response.setId_lesson(lessonsComment.getId());
        response.setContent(lessonsComment.getContent());
        response.setId_user(lessonsComment.getUser().getId());
        return response;
    }
}
