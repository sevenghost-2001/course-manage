package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    long deleteByUserIdAndUsedFalse(int userId); // optional cleanup helper
}
