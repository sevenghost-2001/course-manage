package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.OrderCreationRequest;
import com.udemine.course_manage.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrdersByUser(int userId);
    OrderResponse createOrder(OrderCreationRequest request);
}
