package com.udemine.course_manage.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModuleCreationRequest {
    String title;
    int position;
    int id_course;
    List<LessonsCreatonRequest> lessons;
}
