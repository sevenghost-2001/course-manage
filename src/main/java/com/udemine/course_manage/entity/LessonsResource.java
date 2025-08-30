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
@Table(name = "lesson_resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LessonsResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    @Column(name = "file_url")
    String fileUrl;
    @Column(name = "uploaded_at")
    LocalDateTime uploadedAt;
    @ManyToOne
    @JoinColumn(name = "id_lesson", nullable = false)
    @JsonIgnore
    Lessons lesson;

    @Transient
    @JsonProperty("Lesson_title")
    public String getLessonTitle() {
        return lesson != null ? lesson.getTitle() : null;
    }
    @PrePersist
    public void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }
}
