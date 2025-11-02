package com.udemine.course_manage.dto.request;

import com.udemine.course_manage.dto.response.LessonsResourceCreationResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonsCreatonRequest {
    int id;
    String title;
    MultipartFile video_url;
    double duration;
    double watch_duration;
    int id_module;
    List<LessonResourceCreationRequest> resources;
}
