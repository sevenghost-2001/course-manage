package com.udemine.course_manage.service.Services;


public interface PasswordResetService {
    void requestReset(String email);
    void performReset(String rawToken, String newPassword);
}
