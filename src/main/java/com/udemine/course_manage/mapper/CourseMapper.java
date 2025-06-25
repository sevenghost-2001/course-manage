package com.udemine.course_manage.mapper;


import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
//unmappedTargetPolicy = ReportingPolicy.IGNORE
public interface CourseMapper {
    @Mapping(target = "category", ignore = true) // category gán tay
    @Mapping(source = "image", target = "image")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "cost", target = "cost")
    Course toCourse(CourseCreationRequest request);
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "image", target = "image")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "cost", target = "cost")
    void updateCourse(@MappingTarget Course course, CourseCreationRequest request);
}
