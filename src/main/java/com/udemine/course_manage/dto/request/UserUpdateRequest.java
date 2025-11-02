package com.udemine.course_manage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @NotBlank(message = "NAME_REQUIRED")
    String name;

    @Email(message = "INVALID_EMAIL_FORMAT")
    String email;

    Integer ranks;
    Integer levels;
    Boolean instructor;
    String biography;
    Integer id_role;

    // === Lockout fields ===
    Boolean accountNonLocked;
    Integer failedAttempts;
    Integer lockoutCount;
    String lockTime;
}
