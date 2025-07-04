package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "late_submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LateSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String reason;
    @Column(name = "late_date")
    LocalDate lateDate;
    @ManyToOne
    @JoinColumn(name = "id_submission", nullable = false)
    @JsonIgnore
    Submission submission;

    // Không lưu vào DB
    @Transient
    @JsonProperty("Submission file")
    public String getSubmissionName() {
        return submission != null ? submission.getFileUrl() : null;
    }
    @PrePersist
    public void onCreate() {
        this.lateDate = LocalDate.now();
    }
}
