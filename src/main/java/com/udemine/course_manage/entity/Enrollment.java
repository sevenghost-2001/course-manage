package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "payment_method")
    String paymentMethod;
    @Column(name = "enroll_status")
    String enrollStatus;
    @Column(name = "enrolled_at")
    LocalDateTime enrolledAt;
    @Column(name = "progress_percent")
    int progressPercent;
    @Column(name = "last_updated")
    LocalDateTime lastUpdated;
    @Column(name = "is_certificated")
    boolean isCertificated;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore     // Hide user info when print json out to Postman --> Comment out if we want to keep it
    User user;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    @JsonIgnore
    Course course;

    @PrePersist
    void on_create()  {this.enrolledAt = LocalDateTime.now();}

    // Entity --> Repository --> DTO --> Service(declare nghiep vu cho controller) --> Controller (tiep nhan input)
}
