package com.udemine.course_manage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InstructorResponse {
    String name;
    String avatar;
    String biography;
    int totalCourses;
    int totalStudents;
}
