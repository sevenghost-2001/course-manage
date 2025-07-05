package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.MessageCreationRequest;
import com.udemine.course_manage.entity.Message;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.MessageRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message createMessage(MessageCreationRequest request) {
        Message message = new Message();
        message.setContent(request.getContent());
        User sender = userRepository.findById(request.getSender_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        message.setSender(sender);
        User receiver = userRepository.findById(request.getReceiver_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        message.setReceiver(receiver);

        return messageRepository.save(message);
    }
    public  Message updateMessage(int id, MessageCreationRequest request) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isEmpty()) {
            throw new AppException(ErrorCode.MESSAGE_NOT_FOUND);
        }
        Message message = optionalMessage.get();
        message.setContent(request.getContent());
        User sender = userRepository.findById(request.getSender_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        message.setSender(sender);
        User receiver = userRepository.findById(request.getReceiver_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        message.setReceiver(receiver);
        return messageRepository.save(message);
    }

    public void deleteMessage(int id) {
       if(!messageRepository.existsById(id)) {
            throw new AppException(ErrorCode.MESSAGE_NOT_FOUND);
        }
        messageRepository.deleteById(id);
    }
}
