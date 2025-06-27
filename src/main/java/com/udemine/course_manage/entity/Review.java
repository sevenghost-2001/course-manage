package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double rating;
    String comment;
    LocalDateTime created_at;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    User user;
    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    Course course;
    @PrePersist
    public void onCreate(){
        this.created_at = LocalDateTime.now();
    }
}
