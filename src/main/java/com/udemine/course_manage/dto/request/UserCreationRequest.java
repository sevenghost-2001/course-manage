package com.udemine.course_manage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL_FORMAT") // checks proper email syntax
    String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    @Pattern(
            // no spaces + at least 1 digit, 1 lower, 1 upper, 1 special
            // 8-128 characters
            // Look for library that informs clearly about what is missing
            regexp = "^(?=\\S+$)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^\\w\\s]).{8,128}$",
            message = "PASSWORD_FORMAT_INVALID"
    )
    String password;

    String role;
//    Boolean isInstructor;
    String biography;

    @jakarta.validation.constraints.Min(value = 0, message = "RANKS_INVALID")
    int ranks;
    @jakarta.validation.constraints.Min(value = 0, message = "LEVELS_INVALID")
    int levels;

}

