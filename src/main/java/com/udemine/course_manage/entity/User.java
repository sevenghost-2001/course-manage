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
    @Column(name = "user_id")
     int id;
    @Column(nullable = false)
     String name;
    @Column(nullable = false, unique = true)
     String email;
    @Column(nullable = false)
     String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
     Role role;
    @Column(name = "is_instructor", nullable = false)
     Boolean isInstructor = false;
    @Column(name = "created_at", updatable = false)
     LocalDateTime createAt;
    @PrePersist
    public void onCreate(){
        this.createAt = LocalDateTime.now();
    }
}
