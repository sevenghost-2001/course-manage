package com.udemine.course_manage.service.Imps;


import com.udemine.course_manage.dto.request.NotificationCreationRequest;
import com.udemine.course_manage.entity.Notification;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.NotificationRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImps implements NotificationService {
    @Autowired
    private NotificationRepository notifiRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Notification> getAllNotifications() {
        return notifiRepository.findAll();
    }

    @Override
    public Notification createNotification(NotificationCreationRequest request) {
        // Kiểm tra người dùng có tồn tại không
        Notification notification = new Notification();

        if(notifiRepository.existsByMessage(request.getMessage())){
            throw new AppException(ErrorCode.MESSAGE_EXISTED);
        }
        notification.setMessage(request.getMessage());
        notification.setStatus(request.getStatus());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        notification.setUser(user);
        return notifiRepository.save(notification);
    }

    @Override
    public Notification updateNotification(int id, NotificationCreationRequest request) {
        Notification notification = notifiRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_EXIST));


        notification.setMessage(request.getMessage());
        notification.setStatus(request.getStatus());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.MESSAGE_EXISTED));
        notification.setUser(user);
        return notifiRepository.save(notification);
    }

    @Override
    public void deleteNotification(int id) {
        if (!notifiRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_EXIST);
        }
        notifiRepository.deleteById(id);
    }
}


