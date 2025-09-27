package com.udemine.course_manage.dto.request;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String body) {

}
