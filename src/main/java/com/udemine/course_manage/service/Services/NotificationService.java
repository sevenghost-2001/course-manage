package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.NotificationCreationRequest;
import com.udemine.course_manage.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Notification createNotification(NotificationCreationRequest request);
    Notification updateNotification(int id, NotificationCreationRequest request);
    void deleteNotification(int id);
}
