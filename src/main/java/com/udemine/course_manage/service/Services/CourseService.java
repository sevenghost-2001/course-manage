package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.entity.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseCreationRequest request);
    Course updateCourse(Integer id,CourseCreationRequest request);
    void deleteCourse(Integer id);
    List<Course> getAllCourses();
}
