package com.udemine.course_manage.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonsCommentCreationResponse {
    int id;
    String content;

    UserDto user;                // user object
    LessonDto lesson;            // lesson object
    List<LessonsCommentCreationResponse> replies;

    @Data
    @AllArgsConstructor

    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserDto {
        int id;
        String name;
        String email;
      //  String avatar;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LessonDto {
        int id;
        String title;
    }
}