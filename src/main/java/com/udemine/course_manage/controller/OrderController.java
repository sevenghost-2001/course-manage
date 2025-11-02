package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.OrderCreationRequest;
import com.udemine.course_manage.dto.response.OrderResponse;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    // Admin xem tất cả đơn hàng
    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        ApiResponse<List<OrderResponse>> res = new ApiResponse<>();
        res.setResult(orderService.getAllOrders());
        res.setCode(1000);
        return res;
    }

    //User xem đơn hàng của chính mình
    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUser(@PathVariable int userId) {
        ApiResponse<List<OrderResponse>> res = new ApiResponse<>();
        res.setResult(orderService.getOrdersByUser(userId));
        res.setCode(1000);
        return res;
    }

    //  Tạo đơn hàng mới (từ FE khi thanh toán)
    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request) {
        System.out.println("Creating order for userId=" + request.getId_user());
        System.out.println("orderDetails=" + request.getOrderDetails());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        ApiResponse<OrderResponse> res = new ApiResponse<>();
        res.setResult(orderService.createOrder(request));
        res.setCode(1000);
        return res;
    }
}
