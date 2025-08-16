package com.udemine.course_manage.dto.response;

import com.udemine.course_manage.entity.Course;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomePageResponse {
    List<CareerPathResponse> careerPaths;
    List<CourseResponse> popularCourses;
    List<CourseResponse> newestCourses;
    Map<String, List<CourseResponse>> coursesByCategory;
}
