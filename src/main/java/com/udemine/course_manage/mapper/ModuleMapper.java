package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    @Mapping(target ="title",source ="title")
    @Mapping(target = "position",source = "position")
    @Mapping(target = "course",ignore = true)
    Module toModule(ModuleCreationRequest request);
    void updateModule(@MappingTarget Module module, ModuleCreationRequest request);
}
