package com.udemine.course_manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.OrderDetail;
import com.udemine.course_manage.repository.OrderDetailRepository;
import com.udemine.course_manage.repository.OrderRepository;
import com.udemine.course_manage.service.Services.EmailPublisher;
import com.udemine.course_manage.service.Services.EnrollmentService;
import com.udemine.course_manage.service.Services.MomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MomoService momoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private EmailPublisher emailPublisher;

    @PostMapping("/ipn")
    public ResponseEntity<String> momoIPN(@RequestBody Map<String, Object> payload) {
        System.out.println("Received IPN payload: " + payload);
        String momoOrderId = (String) payload.get("orderId");
        String resultCode = String.valueOf(payload.get("resultCode"));

        // Verify chữ ký
        if (!momoService.verifySignature(payload)) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        // Tách orderId trong DB
        String dbOrderIdStr = momoOrderId.split("_")[0];
        int dbOrderId = Integer.parseInt(dbOrderIdStr);

        orderRepository.findById(dbOrderId).ifPresent(order -> {
            if ("0".equals(resultCode)) {
                order.setStatus("SUCCESS");
                orderRepository.save(order);

                // Tạo Enrollment cho từng course trong order
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(dbOrderId);
                for (OrderDetail detail : orderDetails) {
                    EnrollmentCreationRequest request = new EnrollmentCreationRequest();
                    request.setId_user(order.getUser().getId());
                    request.setId_course(detail.getCourse().getId());
                    request.setPaymentMethod("MOMO"); // hoặc lấy từ payload
                    request.setEnrollStatus("Đã thanh toán");
                    request.setProgressPercent(100);
                    request.setCertificated(false);
                    enrollmentService.createEnrollment(request);
                }

                //  Push thông báo email vào Redis queue
                Map<String, String> emailData = Map.of(
                        "toEmail", order.getUser().getEmail(),
                        "subject", "Đăng ký khóa học thành công",
                        "content", "Bạn đã đăng ký thành công khóa học. Cảm ơn bạn!"
                );
                try {
                    String json = new ObjectMapper().writeValueAsString(emailData);
                    emailPublisher.pushEmail(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                order.setStatus("FAILED");
                orderRepository.save(order);
            }
        });

        return ResponseEntity.ok("IPN received");
    }

    @GetMapping("/result")
    public String resultPage(@RequestParam Map<String, String> params) {
        return "Thanh toán kết thúc với resultCode=" + params.get("resultCode");
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable int orderId) {
        return orderRepository.findById(orderId)
                .map(order -> ResponseEntity.ok(Map.of("status", order.getStatus())))
                .orElse(ResponseEntity.notFound().build());
    }
}
