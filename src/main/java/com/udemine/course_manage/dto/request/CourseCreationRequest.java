package com.udemine.course_manage.dto.request;

import com.udemine.course_manage.entity.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {
    String image;
    String title;
    @Digits(integer = 8, fraction = 2)
    BigDecimal cost;
    int id_category;
}
