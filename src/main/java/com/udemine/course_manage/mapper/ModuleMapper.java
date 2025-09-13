package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.dto.response.ModuleResponse;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleMapper {
    @Autowired
    private CourseRepository courseRepository;
    public Module toModule(ModuleCreationRequest request){
        Module module = new Module();
        module.setTitle(request.getTitle());
        module.setPosition(request.getPosition());
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        module.setCourse(course);
        return module;
    }
    public void updateModule(Module module, ModuleCreationRequest request){
        module.setTitle(request.getTitle());
        module.setPosition(request.getPosition());
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        module.setCourse(course);
    }


}
