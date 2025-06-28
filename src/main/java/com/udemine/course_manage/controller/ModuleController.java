package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;
    //Lấy ra danh sách Modules
    @GetMapping
    ApiResponse<List<Module>> getAllModules(){
        ApiResponse<List<Module>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(moduleService.getAllModules());
        return apiResponse;
    }

    //Thêm Modules
    @PostMapping
    ApiResponse<Module> createModule(@RequestBody ModuleCreationRequest request){
        ApiResponse<Module> apiResponse = new ApiResponse<>();
        apiResponse.setResult(moduleService.createModule(request));
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
