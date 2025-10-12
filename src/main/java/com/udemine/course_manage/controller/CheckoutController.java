package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.CheckoutRequest;
import com.udemine.course_manage.service.Services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@AuthenticationPrincipal Jwt jwt, @RequestBody CheckoutRequest request) {
        {
            try {
                int userId = ((Number) jwt.getClaim("userId")).intValue();
                Map<String, Object> payload = checkoutService.checkout(userId, request.getCarts());
                return ResponseEntity.ok(Map.of(
                        "payUrl", payload.get("payUrl"),
                        "orderId", payload.get("orderId")
                ));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            }
        }
    }
}
