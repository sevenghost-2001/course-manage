package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "carreers_to_courses")
@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CareerToCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "id_career", nullable = false)
    CareerPath careerPath;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    Course course;
}
