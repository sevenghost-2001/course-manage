package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.dto.response.HomePageResponse;
import com.udemine.course_manage.entity.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseCreationRequest request);
    Course updateCourse(Integer id,CourseCreationRequest request);
    void deleteCourse(Integer id);
    List<CourseResponse> getAllCourses();
    HomePageResponse getHomePageData();
    CourseResponse getCourseById(Integer id);
}
