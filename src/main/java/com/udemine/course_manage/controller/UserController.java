package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//    @CrossOrigin("http://localhost:63342")
    @RestController
    @RequestMapping("/api/users")
    public class UserController {
        @Autowired
        private UserService userService;

        @GetMapping
        public ApiResponse<List<User>> getAllUsers(){

            ApiResponse<List<User>> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.getAllUsers());
            return apiResponse;
        }

        @PostMapping
        ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.createUser(request));
            return apiResponse;
        }

        @PutMapping("/{id}")
        public User updateUser(@PathVariable Integer id, @RequestBody UserCreationRequest request){
            return userService.updateUser(id,request);
        }

        @DeleteMapping("/{id}")
        public ApiResponse<String> deleteUser(@PathVariable Integer id) {
            userService.deleteUser(id);
            ApiResponse<String> apiResponse = new ApiResponse<>();
            apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
            apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
            return apiResponse;
        }
    }
