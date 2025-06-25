package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.Course_DiscountCreationRequest;
import com.udemine.course_manage.entity.CourseDiscount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface CourseDiscountMapper {
    @Mapping(target = "course", ignore = true)
    @Mapping(source = "code",target = "code")
    @Mapping(source = "discount_percent",target = "discount_percent")
    @Mapping(source = "start_day",target = "start_day")
    @Mapping(source = "end_day",target = "end_day")
    CourseDiscount toCourseDiscount(Course_DiscountCreationRequest request);
    @Mapping(target = "course", ignore = true)
    @Mapping(source = "code",target = "code")
    @Mapping(source = "discount_percent",target = "discount_percent")
    @Mapping(source = "start_day",target = "start_day")
    @Mapping(source = "end_day",target = "end_day")
    void updateCourseDiscount(@MappingTarget CourseDiscount courseDiscount,Course_DiscountCreationRequest request);
}
