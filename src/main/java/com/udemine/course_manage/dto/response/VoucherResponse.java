package com.udemine.course_manage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VoucherResponse {
    String code;
    String status; // "active", "used", "expired"
    int quantity;
    LocalDateTime time_start;
    LocalDateTime time_end;
}
