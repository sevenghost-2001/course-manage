package com.udemine.course_manage.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemine.course_manage.entity.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SubmissionCreationRequest {
    String fileUrl;
    String submittedAt;
    String status;
    int id_exercise;
    int id_user;

}
