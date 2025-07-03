package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "revenues")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "total_enrollments", precision = 10, scale = 2)
    BigDecimal totalEnrollments;

    @Column(name = "gross_income", precision = 10, scale = 2)
    BigDecimal grossIncome;

    @Column(name = "platform_fee_percent")
    Double platformFeePercent;

    @Column(name = "instructor_earning", precision = 10, scale = 2)
    BigDecimal instructorEarning;

    @Column(name = "revenue_date")
    LocalDateTime revenueDate;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    User user;

    @JsonProperty("Tên giảng viên")
    @Transient // Để không lưu vào database
    public String getUserName() {
        return user != null ? user.getName() : null;
    }

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    @JsonIgnore
    Course course;

    @JsonProperty("Khóa học")
    @Transient
    public String getCourseTitle() {
        return course != null ? course.getTitle() : null;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.revenueDate = LocalDateTime.now(); // Set revenueDate to current time when creating a new revenue record
    }
}
