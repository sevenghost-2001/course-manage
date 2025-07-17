package com.udemine.course_manage.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponseCreationRequest {
    // DTO is what the client sends to the server to create a new lesson response
    String content;
    int user_id; // ID of the user who created the response
    int lesson_comment_id; // ID of the lesson comment to which this response belongs
}
