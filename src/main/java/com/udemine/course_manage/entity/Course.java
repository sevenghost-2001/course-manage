package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
    //@Column(columnDefinition = "TEXT")
     String image;
//    @Column(nullable = false, unique = true)
     String title;
     @Column(name = "short_description",length = 255)
     String shortDescription;
    @Digits(integer = 8, fraction = 2)
    @Column(name = "discount_cost",precision = 10, scale = 2)
    BigDecimal discountedCost;
    @Digits(integer = 8, fraction = 2) // không bắt buộc, dùng để validation
    @Column(precision = 10, scale = 2)
     BigDecimal cost;
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    Category category; // <-- Đây là chỗ đặt @ManyToOne

    @OneToMany(mappedBy = "course")
    List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    List<Review> reviews;

    @OneToMany(mappedBy = "course")
    List<Teach> teaches;

    @OneToMany(mappedBy = "course")
    List<Module> modules;



}
