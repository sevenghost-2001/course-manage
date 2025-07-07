package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.UserRoleCreationRequest;
import com.udemine.course_manage.entity.UserRole;
import com.udemine.course_manage.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    //Xử lý yêu cầu lấy ra danh sách
    @GetMapping
    ApiResponse<List<UserRole>> getAllUserRoles() {
        ApiResponse<List<UserRole>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userRoleService.getAllUserRoles());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<UserRole> createUserRole(@RequestBody UserRoleCreationRequest request) {
        ApiResponse<UserRole> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userRoleService.createUserRole(request));
        return apiResponse;
    }
}
