package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.Course_DiscountCreationRequest;
import com.udemine.course_manage.entity.CourseDiscount;

import java.util.List;

public interface CourseDiscountService {
    CourseDiscount createCourseDiscount(Course_DiscountCreationRequest request);
    CourseDiscount updateCouseDiscount(int id,Course_DiscountCreationRequest request);
    void deleteCourseDiscount(int id);
    List<CourseDiscount> getAllCourseDiscount();
}
