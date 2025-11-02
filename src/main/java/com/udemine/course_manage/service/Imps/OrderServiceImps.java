package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.OrderCreationRequest;
import com.udemine.course_manage.dto.request.OrderDetailRequest;
import com.udemine.course_manage.dto.response.OrderDetailResponse;
import com.udemine.course_manage.dto.response.OrderResponse;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Order;
import com.udemine.course_manage.entity.OrderDetail;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.*;
import com.udemine.course_manage.service.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImps implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByUser(int userId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getUser().getId() == userId)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse createOrder(OrderCreationRequest request) {
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Order order = Order.builder()
                .user(user)
                .totalPrice(request.getTotal_price())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepository.save(order);

        for (OrderDetailRequest detailReq : request.getOrderDetails()) {
            Course course = courseRepository.findById(detailReq.getId_course())
                    .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .course(course)
                    .price(detailReq.getPrice())
                    .quantity(detailReq.getQuantity())
                    .build();

            orderDetailRepository.save(detail);
        }

        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderDetailResponse> details = orderDetailRepository.findByOrderId(order.getId()).stream()
                .map(d -> OrderDetailResponse.builder()
                        .courseTitle(d.getCourse().getTitle())
                        .quantity(d.getQuantity())
                        .price(d.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .userName(order.getUser().getName())
                .email(order.getUser().getEmail())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .details(details)
                .build();
    }
}
