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
@Table(name = "review_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReviewRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String reason;
    String status;
    @Column(name = "reviewed_at")
    LocalDateTime reviewAt;
    @Column(name = "requested_at")
    LocalDateTime requestAt;
    @Column(name = "response_mes")
    String responseMessage;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user", nullable = false)
    User user;
    @ManyToOne
    @JoinColumn(name = "id_submission", nullable = false)
    @JsonIgnore
    Submission submission;

    @Transient
    @JsonProperty("Name of User")
    public String getNameUser() {
        return user != null ? user.getName() : null;
    }

    @Transient
    @JsonProperty("File URL")
    public String getNameSubmission() {
        return submission != null ? submission.getFileUrl() : null;
    }

    @PrePersist
    public void onCreate() {
        this.requestAt = LocalDateTime.now();
        this.reviewAt = null;
    }
}
