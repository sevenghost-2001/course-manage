package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.dto.request.UserUpdateRequest;
import com.udemine.course_manage.dto.request.UserRoleCreationRequest;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Imps.UserServiceImps;
import com.udemine.course_manage.service.Services.UserService;
import com.udemine.course_manage.service.Services.UserRoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAllUsers());
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Integer id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current user: {}", authentication.getName());
        authentication.getAuthorities().forEach(authority -> log.info("Authority: {}", authority.getAuthority()));
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(id));
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<User> createUser(@Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }
    @PutMapping("/unlock/{id}")
    public ApiResponse<User> unlockUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
        user.setLockTime(null);
        user.setLockoutCount(0); //  reset
        userRepository.save(user);

        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(user);
        return apiResponse;
    }

    @PutMapping("/lock/{id}")
    public ApiResponse<User> lockUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        user.setFailedAttempts(3);
        user.setLockoutCount(3); //  khóa vĩnh viễn
        userRepository.save(user);

        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(user);
        return apiResponse;
    }


    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(((UserServiceImps) userService).updateUserByAdmin(id, request));

        // request có id_role (Admin gửi kèm role ID), cập nhật luôn user-role
        if (request.getId_role() != null) {
            UserRoleCreationRequest userRoleRequest = UserRoleCreationRequest.builder()
                    .id_user(id)
                    .id_role(request.getId_role())
                    .build();
            userRoleService.createUserRole(userRoleRequest);
        }

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
