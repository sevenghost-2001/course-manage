package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.entity.Module;

import java.util.List;

public interface ModuleService {
    List<Module> getAllModules();
    Module createModule(ModuleCreationRequest request);
    Module updateModule(int id, ModuleCreationRequest request);
    void deleteModule(int id);
}
