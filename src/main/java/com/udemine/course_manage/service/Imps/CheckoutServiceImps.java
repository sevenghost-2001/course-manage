package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.CartItemRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Order;
import com.udemine.course_manage.entity.OrderDetail;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.*;
import com.udemine.course_manage.service.Services.CheckoutService;
import com.udemine.course_manage.service.Services.MomoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CheckoutServiceImps implements CheckoutService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MomoService momoService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional
    @Override
    public Map<String, Object> checkout(int userId, List<CartItemRequest> cartItems) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (cartItems == null || cartItems.isEmpty()) {
            throw new AppException(ErrorCode.EMPTY_CART);
        }
        //  Kiểm tra user đã mua course nào chưa
        for (CartItemRequest c : cartItems) {
            boolean exists = enrollmentRepository.existsByUserIdAndCourseId(userId, c.getCourseId());
            if (exists) {
                throw new AppException(ErrorCode.USER_ALREADY_ENROLLED);
            }
        }
        double total = cartItems.stream()
                .mapToDouble(c -> c.getPrice() * c.getQuantity())
                .sum();

        // Tạo order
        Order order = Order.builder()
                .user(user)
                .totalPrice(total)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        // Lưu OrderDetail (nhiều item)
        for (CartItemRequest c : cartItems) {
            Course course = courseRepository.findById(c.getCourseId())
                    .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .course(course)
                    .quantity(c.getQuantity())
                    .price(c.getPrice())
                    .build();
            orderDetailRepository.save(detail);
        }
        Map<String, Object> momoPayload = momoService.createPayment(order.getId(), total);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                simulateMomoIPN(order.getId());
                System.out.println("Giao dịch hoàn tất. Có thể redirect frontend.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return momoService.createPayment(order.getId(), total);
    }
    private void simulateMomoIPN(int orderId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> ipnPayload = new HashMap<>();
            ipnPayload.put("orderId", orderId + "_MOCK");
            ipnPayload.put("resultCode", "0"); // 0 = thành công
            ipnPayload.put("accessKey", "mock_access");
            ipnPayload.put("amount", "100000");
            ipnPayload.put("extraData", "");
            ipnPayload.put("message", "Successful.");
            ipnPayload.put("orderInfo", "Fake IPN test");
            ipnPayload.put("orderType", "momo_wallet");
            ipnPayload.put("partnerCode", "MOMOTEST");
            ipnPayload.put("payType", "mock");
            ipnPayload.put("requestId", UUID.randomUUID().toString());
            ipnPayload.put("responseTime", System.currentTimeMillis());
            ipnPayload.put("transId", UUID.randomUUID().toString());
            ipnPayload.put("signature", "mock_signature");

            restTemplate.postForEntity("http://localhost:8080/payment/ipn", ipnPayload, String.class);
//            System.out.println(" IPN giả lập đã được gửi cho order " + orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
