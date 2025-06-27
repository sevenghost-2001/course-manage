package com.udemine.course_manage.controller;


import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.RoleCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PostMapping
    ApiResponse<Role> createRole(@RequestBody @Valid RoleCreationRequest request){
        ApiResponse<Role> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.createRole(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Integer id, @RequestBody RoleCreationRequest request){
        return roleService.updateRole(id,request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
