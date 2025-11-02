package com.udemine.course_manage.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    int orderId;
    String userName;
    String email;
    double totalPrice;
    String status;
    LocalDateTime createdAt;
    List<OrderDetailResponse> details;
}
