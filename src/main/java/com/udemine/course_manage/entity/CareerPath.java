package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "career_paths")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CareerPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    String image;

    @OneToMany(mappedBy = "careerPath")
    List<CarreerToCourse> carreerToCourses;
}
