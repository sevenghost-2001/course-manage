package com.udemine.course_manage.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "password_reset_tokens",
        indexes = {
                @Index(name = "idx_prt_user", columnList = "userId"),
                @Index(name = "idx_prt_expires", columnList = "expiresAt")
        })
@Getter @Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference your existing User id (no FK here to keep it simple; you can map @ManyToOne if you prefer)
    @Column(nullable = false)
    private Long userId;

    // Base64url(SHA-256) of the raw token; ~43 chars, allow cushion
    @Column(nullable = false, unique = true, length = 64)
    private String tokenHash;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
