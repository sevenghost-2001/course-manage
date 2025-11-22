package com.udemine.course_manage.dto.response;

import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.entity.Voucher;
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
import java.time.LocalDateTime;
import java.util.List;

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
    String description;
    @Digits(integer = 8, fraction = 2)
    @Column(precision = 10, scale = 2)
    BigDecimal discountedCost;
    @Digits(integer = 8, fraction = 2) // không bắt buộc, dùng để validation
    @Column(precision = 10, scale = 2)
    BigDecimal cost;
    String nameCategory;
    //số điểm trung bình của khóa học
    BigDecimal averageRating;
    //số người đã đánh giá khóa học
    int totalRatings;
    //Tên giảng viên khóa học
    String instructorName;
    LocalDateTime created_at;
    int totalEnrollments;
    //Voucher của khóa học
    String voucher;
    String videoDemo;
    boolean isCertification;
    double totalTimeModules;
    List<ModuleResponse> modules;
    List<InstructorResponse> instructors;
    List<ReviewResponse> reviews;
//    List<LessonsResourceCreationResponse> resources;
}
