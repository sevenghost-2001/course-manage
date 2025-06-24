package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
    @Column(name = "fullname", nullable = false)
     String name;
    @Column(nullable = false, unique = true)
     String email;
    @Column(name = "passwords", nullable = false)
     String password;
    @Column(nullable = false,name = "roles")
     String role;
    @Column(name = "is_instructor", nullable = false)
     Boolean isInstructor = false;

    @Column(updatable = false)
     LocalDateTime updated_at;
    String biography;
    int ranks;
    int levels;


    @PrePersist
    public void onCreate(){
        this.updated_at = LocalDateTime.now();
    }
}
