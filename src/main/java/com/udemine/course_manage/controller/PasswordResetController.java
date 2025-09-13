package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ForgotPasswordRequest;
import com.udemine.course_manage.dto.request.ResetPasswordRequest;
import com.udemine.course_manage.dto.response.ApiMessageResponse;
import com.udemine.course_manage.service.Services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiMessageResponse> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        // Always 200 to avoid email enumeration
        passwordResetService.requestReset(req.getEmail());
        return ResponseEntity.ok(new ApiMessageResponse("If the email exists, a reset link has been sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiMessageResponse> resetPassword(@RequestBody ResetPasswordRequest req) {
        passwordResetService.performReset(req.getToken(), req.getNewPassword());
        return ResponseEntity.ok(new ApiMessageResponse("Password has been reset."));
    }
}
