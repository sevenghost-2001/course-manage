package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    boolean existsByMessage(String message);
    boolean existsByStatus(String status);
}
