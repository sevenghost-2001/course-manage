package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.dto.request.UserUpdateRequest;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.UserServiceImps;
import com.udemine.course_manage.service.Services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//    @CrossOrigin("http://localhost:63342")
    //để inject logger vào class
    @Slf4j
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

        @GetMapping("/{id}")
        public ApiResponse<User> getUserById(@PathVariable Integer id) {
            //Dùng SecurityContextHolder để lấy thông tin người dùng hiện tại
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("Current user: {}", authentication.getName());
            authentication.getAuthorities().forEach(authority -> {
                log.info("Authority: {}", authority.getAuthority());
            });
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.getUserById(id));
            return apiResponse;
        }
        public ApiResponse<User> apiResponse = new ApiResponse<>();

        // This is signup endpoint, used to create a new user
        @PostMapping
        ApiResponse<User> createUser(@Valid UserCreationRequest request){
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.createUser(request));
            return apiResponse;
        }

        @PutMapping("/{id}")
        public ApiResponse<User> updateUser(
                @PathVariable Integer id,
                @RequestBody @Valid UserUpdateRequest request
        ) {
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(((UserServiceImps) userService).updateUserByAdmin(id, request));
            return apiResponse;
        }

        @DeleteMapping("/{id}")
        public ApiResponse<String> deleteUser(@PathVariable Integer id) {
            userService.deleteUser(id);
            ApiResponse<String> apiResponse = new ApiResponse<>();
            apiResponse.setCode(1000);
            apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
            return apiResponse;
        }

    }
