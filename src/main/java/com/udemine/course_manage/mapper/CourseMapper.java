package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    @Autowired
    private CategoryRepository categoryRepository;
    public Course toCourse(CourseCreationRequest request) {
        Course course = new Course();
        course.setImage(request.getImage());
        course.setTitle(request.getTitle());
        course.setShortDescription(request.getShortDescription());
        course.setCost(request.getCost());
        course.setDiscountedCost(request.getDiscountedCost());
        Category category = categoryRepository.findById(request.getId_category())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        course.setCategory(category);
        return course;
    }

    public void updateCourse(Course course, CourseCreationRequest request) {
        course.setImage(request.getImage());
        course.setTitle(request.getTitle());
        course.setShortDescription(request.getShortDescription());
        course.setCost(request.getCost());
        course.setDiscountedCost(request.getDiscountedCost());
        Category category = categoryRepository.findById(request.getId_category())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        course.setCategory(category);

    }

    public CourseResponse toCourseResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setShortDescription(course.getShortDescription());
        response.setCost(course.getCost());
        response.setDiscountedCost(course.getDiscountedCost());
        response.setImage(course.getImage());
        response.setNameCategory(course.getCategory().getName());
        return response;
    }
}
