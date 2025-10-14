package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.CartItemRequest;

import java.util.List;
import java.util.Map;

public interface CheckoutService {
    public Map<String, Object> checkout(int userId, List<CartItemRequest> cartItems);
}
