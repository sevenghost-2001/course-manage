package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "role", expression = "java(com.udemine.course_manage.entity.Role.valueOf(request.getRole().toLowerCase()))")
    @Mapping(target = "isInstructor", expression = "java(request.getIsInstructor() != null ? request.getIsInstructor() : false)")
    User toUser(UserCreationRequest request);
    @Mapping(target = "role",expression = "java(com.udemine.course_manage.entity.Role.valueOf(request.getRole().toLowerCase()))")
    @Mapping(target = "isInstructor", expression = "java(request.getIsInstructor() != null ? request.getIsInstructor() : false)")
    void updateUser(@MappingTarget User user, UserCreationRequest request);
}
