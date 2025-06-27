package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore//Ẩn đi user
    User user;
    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    @JsonIgnore//Ẩn đi course
    Course course;

    //Thêm 2 thuộc tính để trả về name và title
    @Transient
    @JsonProperty("Tên học viên")//Tên trong ngoặc kép muốn đặt sao cũng được
    public String getUserName() {
        return user != null ? user.getName() : null;
    }

    @Transient
    @JsonProperty("Khóa học")
    public String getCourseTitle() {
        return course != null ? course.getTitle() : null;
    }

    @PrePersist
    public void onCreate(){
        this.created_at = LocalDateTime.now();
    }
}
