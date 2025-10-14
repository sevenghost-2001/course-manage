package com.udemine.course_manage.service.Services;

public interface EmailService {
    public void sendOrderSuccessEmail(String toEmail, String subject, String content);
}
