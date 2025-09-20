package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
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
    @Digits(integer = 1, fraction = 2) // ví dụ: 5.00
    @Column(name = "average_rating", precision = 3, scale = 2)
    BigDecimal averageRating;
    LocalDateTime created_at;
    String videoDemo;
    String imgVideoDemo;
    @Column(name="Certification")
    boolean is_certification;
    @Column(name = "total_time_duration")
    double totalTimeModules; // Tổng thời gian của tất cả các module trong khóa học, tính bằng giờ
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    Category category; // <-- Đây là chỗ đặt @ManyToOne
    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany(mappedBy = "course")
    List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    List<Teach> teaches = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    List<CareerToCourse> careerToCourses = new ArrayList<>();

    @PrePersist
    void on_create(){
        this.created_at = LocalDateTime.now();
    }

}
