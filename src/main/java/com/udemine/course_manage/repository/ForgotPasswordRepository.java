package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.ForgotPassword;
import com.udemine.course_manage.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("SELECT fp FROM ForgotPassword fp WHERE fp.otp = ?1 AND fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForgotPassword fp WHERE fp.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForgotPassword fp WHERE fp.expirationTime < CURRENT_TIMESTAMP")
    void deleteAllExpired();
}