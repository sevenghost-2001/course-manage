package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.service.Services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImps implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void sendOrderSuccessEmail(String toEmail, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
