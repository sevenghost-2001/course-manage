package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "scoring")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Scoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Double grade;
    String feedback;
    @Column(name = "graded_at")
    LocalDateTime gradedAt;
    @ManyToOne
    @JoinColumn(name = "id_submission", nullable = false)
    @JsonIgnore
    Submission submission;

    @ManyToOne
    @JoinColumn(name = "id_instructor", nullable = false)
    @JsonIgnore
    User user;

    @Transient
    @JsonProperty("Submission file")
    public String getSubmissionFile() {
        return submission != null ? submission.getFileUrl() : null;
    }

    @Transient
    @JsonProperty("Submission user")
    public String getSubmissionUser() {
        return user != null ? user.getName() : null;
    }
    @PrePersist
    public void onCreate() {
        this.gradedAt = LocalDateTime.now();
    }
}
