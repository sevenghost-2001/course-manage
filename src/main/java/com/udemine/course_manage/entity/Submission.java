package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "file_url")
    String fileUrl;
    @Column(name = "submitted_at")
    String submittedAt;
    String status;
    @ManyToOne
    @JoinColumn(name = "id_exercise", nullable = false)
    @JsonIgnore
    Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    User user;

    @Transient
    @JsonProperty("Fullname")
    public String getUserFullName() {
        return user != null ? user.getName() : null;
    }
    @Transient
    @JsonProperty("Exercise Title")
    public String getExerciseTitle() {
        return exercise != null ? exercise.getTitle() : null;
    }
}
