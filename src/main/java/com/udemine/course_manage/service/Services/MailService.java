package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.MailBody;

public interface MailService {
    void sendSimpleMessage(MailBody mailBody);
}
