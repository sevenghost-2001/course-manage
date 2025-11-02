package com.udemine.course_manage.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LessonCommentCreationRequest {

    String content;
    Integer id_user;
    Integer id_lesson;
    Integer id_parent;
}
