package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.MailBody;
import com.udemine.course_manage.service.Services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage(MailBody mailBody) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(mailBody.to());
       message.setFrom("backend.skillgo@gmail.com"); // the email configured in application.properties
       message.setSubject(mailBody.subject());
       message.setText(mailBody.body());

       javaMailSender.send(message);
    }
}

