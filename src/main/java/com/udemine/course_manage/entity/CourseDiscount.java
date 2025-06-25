package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "course_discounts")
public class CourseDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String code;
    double discount_percent;
    LocalDate start_day;
    LocalDate end_day;
    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    Course course; // <-- Đây là chỗ đặt @ManyToOne
}
