package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    @Column(name = "position_module")
    int position;
    LocalDateTime created_at;
    @ManyToOne
    @JoinColumn(name = "id_course",nullable = false)
    @JsonIgnore
    Course course;

    @JsonProperty("Khóa học")//Tên trong ngoặc kép muốn đặt sao cũng được
    public String getNameCourse() {
        return course != null ? course.getTitle(): null;
    }
    @PrePersist
    void on_create(){
        this.created_at = LocalDateTime.now();
    }
}

