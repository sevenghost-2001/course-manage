package com.udemine.course_manage.utils;

import com.udemine.course_manage.repository.ForgotPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OtpCleanupJob {
    private final ForgotPasswordRepository forgotPasswordRepository;

    // Run every hour (00 min)
    @Scheduled(cron = "0 0,03 * * * *") // At minute 0 of every hour
    @Transactional
    public void purgeExpiredOtps() {
        forgotPasswordRepository.deleteAllExpired();
    }
}