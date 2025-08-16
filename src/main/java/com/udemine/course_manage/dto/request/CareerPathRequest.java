package com.udemine.course_manage.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CareerPathRequest {
    String name;
    @Lob
    @Column(columnDefinition = "TEXT")
    String description;
    String image;
    List<Integer> courseIds; // Danh sách ID của các khóa học trong lộ trình nghề nghiệp này
}
