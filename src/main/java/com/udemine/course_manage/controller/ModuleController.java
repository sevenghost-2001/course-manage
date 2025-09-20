package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.dto.response.ModuleResponse;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.ModuleMapper;
import com.udemine.course_manage.service.Imps.ModuleServiceImps;
import com.udemine.course_manage.service.Services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModuleMapper moduleMapper;
    //Lấy ra danh sách Modules
    @GetMapping
    ApiResponse<List<Module>> getAllModules(){
        ApiResponse<List<Module>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(moduleService.getAllModules());
        return apiResponse;
    }

    //Thêm Modules
    @PostMapping
    ApiResponse<ModuleResponse> createModule(@RequestBody ModuleCreationRequest request){
        ApiResponse<ModuleResponse> apiResponse = new ApiResponse<>();
        Module module = moduleService.createModule(request);
        ModuleResponse moduleResponse = moduleMapper.toModuleResponse(module);
        apiResponse.setResult(moduleResponse);
        return apiResponse;
    }

    //Sửa Modules
    @PutMapping("/{id}")
    ApiResponse<Module> updateModule(@PathVariable int id, @RequestBody ModuleCreationRequest request){
        ApiResponse<Module> apiResponse = new ApiResponse<>();
        apiResponse.setResult(moduleService.updateModule(id,request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteModule(@PathVariable int id){
        moduleService.deleteModule(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
