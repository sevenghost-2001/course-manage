package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.MessageCreationRequest;
import com.udemine.course_manage.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();
    Message createMessage(MessageCreationRequest request);
    Message updateMessage(int id, MessageCreationRequest request);
    void deleteMessage(int id);
}
