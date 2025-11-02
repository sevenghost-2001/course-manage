package com.udemine.course_manage.controller;


import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.Enrollment;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.EnrollmentServiceImps;
import com.udemine.course_manage.service.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    ApiResponse<List<Enrollment>> getAllEnrollments() {
        ApiResponse<List<Enrollment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(enrollmentService.getAllEnrollments());
        return apiResponse;
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Enrollment>> getEnrollmentsByUser(@PathVariable int userId) {
        ApiResponse<List<Enrollment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(enrollmentService.getEnrollmentsByUser(userId));
        return apiResponse;
    }
    @GetMapping("/buyers")
    public ApiResponse<List<Map<String, Object>>> getUsersWhoBought() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        // Dùng LinkedHashMap để giữ thứ tự
        Map<Integer, Map<String, Object>> buyerMap = new LinkedHashMap<>();

        for (Enrollment e : enrollments) {
            if (e.getUser() == null) continue;

            int userId = e.getUser().getId();
            buyerMap.putIfAbsent(userId, new LinkedHashMap<>());

            Map<String, Object> info = buyerMap.get(userId);
            info.put("id", userId);
            info.put("name", e.getUser().getName());
            info.put("email", e.getUser().getEmail());
            info.put("accountNonLocked", e.getUser().isAccountNonLocked());

            // Nếu chưa có courseCount thì set = 1, nếu có rồi thì +1
            info.put("courseCount", (int) info.getOrDefault("courseCount", 0) + 1);
        }

        ApiResponse<List<Map<String, Object>>> res = new ApiResponse<>();
        res.setCode(1000);
        res.setResult(new ArrayList<>(buyerMap.values()));
        return res;
    }

    @PostMapping
    ApiResponse<Enrollment> createEnrollment(@RequestBody EnrollmentCreationRequest request) {
        ApiResponse<Enrollment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(enrollmentService.createEnrollment(request));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<Enrollment> updateEnrollment(@PathVariable int id,@RequestBody EnrollmentCreationRequest request){
        ApiResponse<Enrollment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(enrollmentService.updateEnrollment(id,request));
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteEnrollment(@PathVariable int id){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        enrollmentService.deleteEnrollment(id);
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

}
