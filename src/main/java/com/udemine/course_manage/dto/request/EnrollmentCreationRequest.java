package com.udemine.course_manage.dto.request;


import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
// Things we need to input
public class EnrollmentCreationRequest {

    String paymentMethod;
    String enrollStatus;
//    LocalDateTime enrolledAt; // In Entity, we already have [void on_create()  {this.enrolledAt = LocalDateTime.now();}]
    int progressPercent;
//    LocalDateTime lastUpdated;
    boolean isCertificated;
    int id_user;
    int id_course;
}
