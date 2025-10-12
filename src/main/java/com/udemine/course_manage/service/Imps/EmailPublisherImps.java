package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.service.Services.EmailPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailPublisherImps implements EmailPublisher {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void pushEmail(String emailJson) {
        redisTemplate.opsForList().rightPush("emailQueue", emailJson);
    }
}
