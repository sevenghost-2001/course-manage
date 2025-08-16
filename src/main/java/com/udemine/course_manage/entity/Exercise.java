package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    LocalDateTime timestart;
    Double deadline;
    @ManyToOne
    @JoinColumn(name = "id_lesson", nullable = false)
    @JsonIgnore
    Lessons lesson;

    //Không lưu vào DB
    @JsonProperty("Lesson_name")
    public String getLessonName() {
        return lesson != null ? lesson.getTitle() : null;
    }
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.timestart = LocalDateTime.now();
    }
}
