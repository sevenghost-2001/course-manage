package com.udemine.course_manage.dto.response;

import com.udemine.course_manage.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CourseResponse {
    int id;
    String image;
    String title;
    @Column(length = 5000)
    String shortDescription;
    @Digits(integer = 8, fraction = 2)
    @Column(precision = 10, scale = 2)
    BigDecimal discountedCost;
    @Digits(integer = 8, fraction = 2) // không bắt buộc, dùng để validation
    @Column(precision = 10, scale = 2)
    BigDecimal cost;
    String nameCategory; // <-- Đây là chỗ đặt @ManyToOne
}
