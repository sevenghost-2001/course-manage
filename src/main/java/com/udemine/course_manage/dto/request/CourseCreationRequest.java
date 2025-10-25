package com.udemine.course_manage.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemine.course_manage.entity.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {
    MultipartFile image;
    String title;
    @Column(length = 5000)
    String shortDescription;
    String description;
    @Column(precision = 10, scale = 2)
    BigDecimal discountedCost;
    @Column(precision = 10, scale = 2)
    BigDecimal cost;
    int id_category;
    MultipartFile videoDemo;
    Boolean isCertification;
    List<ModuleCreationRequest> modules;
    @JsonIgnore // Không ánh xạ trực tiếp từ form, chỉ dùng để nhận JSON
    private String modulesJson;

    // Getters, setters...
    public void setModulesJson(String modulesJson) {
        this.modulesJson = modulesJson;
    }

    public String getModulesJson() {
        return modulesJson;
    }
}
