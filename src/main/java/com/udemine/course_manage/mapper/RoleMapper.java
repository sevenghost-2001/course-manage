package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.RoleCreationRequest;
import com.udemine.course_manage.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    @Mapping(target = "name", source = "name")
    Role toRole(RoleCreationRequest request);

    @Mapping(target = "name", source = "name")
    void updateRole(@MappingTarget Role role, RoleCreationRequest request);
}
