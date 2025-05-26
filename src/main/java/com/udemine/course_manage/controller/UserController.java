package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/users")
    public class UserController {
        @Autowired
        private UserService userService;

        @GetMapping
        public List<User> getAllUsers(){
            return userService.getAllUsers();
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
        public String deleteUser(@PathVariable Integer id) {
            userService.deleteUser(id);
            return "Deleted user with id: " + id;
        }
    }
