package com.udemine.course_manage.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueCreationRequest {
    @Column(precision = 10, scale = 2)
    BigDecimal totalEnrollments;

    @Column(precision = 10, scale = 2)
    BigDecimal grossIncome;

    Double platformFeePercent;

    @Column(precision = 10, scale = 2)
    BigDecimal instructorEarning;

    int id_user;
    int id_course;
}
