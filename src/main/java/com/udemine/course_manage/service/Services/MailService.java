package com.udemine.course_manage.service.Services;

public interface MailService {
    void sendPlainText(String to, String subject, String body);
}
