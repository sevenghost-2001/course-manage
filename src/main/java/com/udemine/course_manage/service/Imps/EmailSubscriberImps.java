package com.udemine.course_manage.service.Imps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemine.course_manage.service.Services.EmailService;
import com.udemine.course_manage.service.Services.EmailSubscriber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmailSubscriberImps implements EmailSubscriber {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @PostConstruct
    public void listenQueue() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                // BLPOP: block until element is available
                String emailJson = redisTemplate.opsForList().leftPop("emailQueue", 0, TimeUnit.SECONDS);
                if (emailJson != null) {
                    try {
                        Map<String, String> data = objectMapper.readValue(emailJson, Map.class);
                        emailService.sendOrderSuccessEmail(
                                data.get("toEmail"),
                                data.get("subject"),
                                data.get("content")
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
