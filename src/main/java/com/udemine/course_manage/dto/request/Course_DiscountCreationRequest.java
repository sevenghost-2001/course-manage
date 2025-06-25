package com.udemine.course_manage.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course_DiscountCreationRequest {
    String code;
    double discount_percent;
    LocalDate start_day;
    LocalDate end_day;
    int id_course;
}
