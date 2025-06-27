package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.ReviewCreationRequest;
import com.udemine.course_manage.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.swing.*;

@Mapper(componentModel = "Spring")
public interface ReviewMapper {
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "course",ignore = true)
    @Mapping(target = "rating",source = "rating")
    @Mapping(target = "comment",source = "comment")
    @Mapping(target = "created_at",ignore = true)
    Review toReview(ReviewCreationRequest request);
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "course",ignore = true)
    @Mapping(target = "rating",source = "rating")
    @Mapping(target = "comment",source = "comment")
    @Mapping(target = "created_at",ignore = true)
    void updateReview(@MappingTarget Review review, ReviewCreationRequest request);
}
