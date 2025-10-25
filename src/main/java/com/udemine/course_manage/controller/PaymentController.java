package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.OrderDetail;
import com.udemine.course_manage.repository.EnrollmentRepository;
import com.udemine.course_manage.repository.OrderDetailRepository;
import com.udemine.course_manage.repository.OrderRepository;
import com.udemine.course_manage.service.Services.EmailService;
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
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private MomoService momoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/ipn")
    public ResponseEntity<String> momoIPN(@RequestBody Map<String, Object> payload) {
        System.out.println("Received IPN payload: " + payload);
        String momoOrderId = (String) payload.get("orderId");
        String resultCode = String.valueOf(payload.get("resultCode"));

        // Tách orderId trong DB
        String dbOrderIdStr = momoOrderId.split("_")[0];
        int dbOrderId = Integer.parseInt(dbOrderIdStr);

        //  Kiểm tra xem order đã xử lý thành công trước đó chưa
        var optionalOrder = orderRepository.findById(dbOrderId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        var order = optionalOrder.get();

        // Nếu đơn hàng đã SUCCESS -> bỏ qua IPN trùng
        if ("SUCCESS".equals(order.getStatus())) {
            System.out.println(" IPN trùng lặp, bỏ qua xử lý orderId=" + dbOrderId);
            return ResponseEntity.ok("Duplicate IPN ignored");
        }

        //  Xử lý IPN mới
        if ("0".equals(resultCode)) {
            order.setStatus("SUCCESS");
            orderRepository.save(order);

            // Tạo Enrollment nếu chưa có
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(dbOrderId);
            for (OrderDetail detail : orderDetails) {
                int userId = order.getUser().getId();
                int courseId = detail.getCourse().getId();

                if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
                    System.out.println(" User đã đăng ký khóa học, bỏ qua");
                    continue;
                }

                EnrollmentCreationRequest request = new EnrollmentCreationRequest();
                request.setId_user(userId);
                request.setId_course(courseId);
                request.setPaymentMethod("MOMO");
                request.setEnrollStatus("Đã thanh toán");
                request.setProgressPercent(100);
                request.setCertificated(false);

                enrollmentService.createEnrollment(request);
            }

            // Gửi email xác nhận
            String to = order.getUser().getEmail();
            String subject = "Đăng ký khóa học thành công";
            String content = "Xin chào " + order.getUser().getName() + ",\n\n"
                    + "Bạn đã đăng ký thành công khóa học của SkillGo.\n"
                    + "Cảm ơn bạn đã tin tưởng và ủng hộ!\n\n"
                    + "— SkillGo Team";

            emailService.sendOrderSuccessEmail(to, subject, content);
            System.out.println(" Đã gửi email đến: " + to);
        } else {
            order.setStatus("FAILED");
            orderRepository.save(order);
        }

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
