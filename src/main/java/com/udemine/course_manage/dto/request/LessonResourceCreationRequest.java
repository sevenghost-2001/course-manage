package com.udemine.course_manage.dto.request;

import com.udemine.course_manage.dto.response.LessonsResourceCreationResponse;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LessonResourceCreationRequest {
    String title;
    MultipartFile fileUrl;
    int id_lesson;
    LessonsCreatonRequest resources;
}
