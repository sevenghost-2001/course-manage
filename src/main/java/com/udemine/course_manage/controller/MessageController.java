package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.MessageCreationRequest;
import com.udemine.course_manage.entity.Message;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Imps.MessageServiceImps;
import com.udemine.course_manage.service.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    ApiResponse<List<Message>> getAllMessages() {
        ApiResponse<List<Message>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(messageService.getAllMessages());
        return apiResponse;
    }

    @PostMapping
    ApiResponse<Message> createMessage(@RequestBody MessageCreationRequest request) {
        // @RequestBody để map các dữ liệu trong body vào biến request
        ApiResponse<Message> apiResponse = new ApiResponse<>();
        apiResponse.setResult(messageService.createMessage(request));
        return apiResponse;
    }

    // Update message
    @PutMapping("/{id}")
    ApiResponse<Message> updateMessage(@PathVariable int id, @RequestBody MessageCreationRequest request) {
        ApiResponse<Message> apiResponse = new ApiResponse<>();
        apiResponse.setResult(messageService.updateMessage(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteMessage(@PathVariable int id) {
        messageService.deleteMessage(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

}
