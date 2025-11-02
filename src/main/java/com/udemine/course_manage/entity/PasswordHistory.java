package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "password_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PasswordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // users.id is INT in your DB
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}

