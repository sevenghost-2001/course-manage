package com.udemine.course_manage.dto.request;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequestCreationRequest {
    String reason;
    String status;
    String responseMessage;
    int id_user;
    int id_submission;
}
