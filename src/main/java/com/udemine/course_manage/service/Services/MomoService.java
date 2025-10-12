package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.MomoCreationRequest;
import com.udemine.course_manage.dto.response.MomoResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface MomoService {
    public Map<String, Object> createPayment(int orderId, double amount);
    public boolean verifySignature(Map<String, Object> payload);
}
