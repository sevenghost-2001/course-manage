package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.entity.Notification;
import com.udemine.course_manage.dto.request.NotificationCreationRequest;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.NotificationServiceImps;
import com.udemine.course_manage.service.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping
    ApiResponse<List<Notification>> getAllNotifications(){
        ApiResponse<List<Notification>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(notificationService.getAllNotifications());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Notification> createNotification(@RequestBody NotificationCreationRequest request) {
        ApiResponse<Notification> apiResponse = new ApiResponse<>();
        apiResponse.setResult(notificationService.createNotification(request));
        return apiResponse;
    }
    @PutMapping("/{id}")
    ApiResponse<Notification> updateNotification(@PathVariable int id,@RequestBody NotificationCreationRequest request){
        ApiResponse<Notification> apiResponse = new ApiResponse<>();
        apiResponse.setResult(notificationService.updateNotification(id,request));
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteNotification(@PathVariable int id){
        notificationService.deleteNotification(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }
}
