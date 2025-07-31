package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.MessageCreationRequest;
import com.udemine.course_manage.entity.Message;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.MessageRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImps implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
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

    @Override
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

    @Override
    public void deleteMessage(int id) {
       if(!messageRepository.existsById(id)) {
            throw new AppException(ErrorCode.MESSAGE_NOT_FOUND);
        }
        messageRepository.deleteById(id);
    }
}
