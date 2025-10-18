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
        response.setId(lessonsComment.getId());
        response.setContent(lessonsComment.getContent());

        LessonsCommentCreationResponse.UserDto userDto =
                LessonsCommentCreationResponse.UserDto.builder()
                        .id(lessonsComment.getUser().getId())
                        .name(lessonsComment.getUser().getName())
                        .email(lessonsComment.getUser().getEmail())
                     //   .avatar(lessonsComment.getUser().getAvatar())
                        .build();
        response.setUser(userDto);

        LessonsCommentCreationResponse.LessonDto lessonDto =
                LessonsCommentCreationResponse.LessonDto.builder()
                        .id(lessonsComment.getLesson().getId())
                        .title(lessonsComment.getLesson().getTitle())
                        .build();
        response.setLesson(lessonDto);

        // replies đệ quy
        if (lessonsComment.getReplies() != null && !lessonsComment.getReplies().isEmpty()) {
            response.setReplies(
                    lessonsComment.getReplies()
                            .stream()
                            .map(this::toLessonCommentCreationResponse)
                            .toList()
            );
        }
        return response;
    }
}

