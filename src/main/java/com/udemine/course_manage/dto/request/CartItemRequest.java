package com.udemine.course_manage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartItemRequest {
    private int courseId;
    private int quantity;
    private double price;
}
