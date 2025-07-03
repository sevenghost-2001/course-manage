package com.udemine.course_manage.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonsCreatonRequest {
    int id;
    String title;
    String video_url;
    double duration;
    double watch_duration;
    int id_module;

}
