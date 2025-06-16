package com.udemine.course_manage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String name;
     String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
     String password;
     String role; // sẽ convert thành Enum Role trong service
     Boolean isInstructor;

}

