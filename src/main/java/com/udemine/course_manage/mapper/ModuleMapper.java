package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.dto.response.ModuleResponse;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleMapper {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public Module toModule(ModuleCreationRequest request){
        Module module = new Module();
        module.setTitle(request.getTitle());
        module.setPosition(request.getPosition());
        return module;
    }
    public void updateModule(Module module, ModuleCreationRequest request){
        module.setTitle(request.getTitle());
        module.setPosition(request.getPosition());
    }
    public ModuleResponse toModuleResponse(Module module){
        ModuleResponse response = new ModuleResponse();
        response.setTitle(module.getTitle());
        response.setPosition(module.getPosition());
        response.setCreated_at(module.getCreated_at());
        double totalTimeLessons = module.getLessons() != null
                ? module.getLessons().stream()
                .mapToDouble(lesson -> lesson.getDuration() != 0 ? lesson.getDuration() : 0)
                .sum()
                : 0.0;
        response.setTotalTimeLessons(totalTimeLessons);
        return response;
    }





}
