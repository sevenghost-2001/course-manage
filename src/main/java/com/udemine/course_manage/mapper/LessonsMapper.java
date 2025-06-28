package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LessonsMapper {
    @Mapping(target ="title",source ="title")
    @Mapping(target = "video_url",source = "video_url")
    @Mapping(target = "duration",source = "duration")
    @Mapping(target = "watch_duration",source = "watch_duration")
    @Mapping(target = "module",ignore = true)
    Lessons toLessons(LessonsCreatonRequest request);
    void updateLessons(@MappingTarget Lessons lessons, LessonsCreatonRequest request);
}

