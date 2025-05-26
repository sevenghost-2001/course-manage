package com.udemine.course_manage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String name;

    private String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String role; // sẽ convert thành Enum Role trong service
    private Boolean isInstructor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getInstructor() {
        return isInstructor;
    }

    public void setInstructor(Boolean instructor) {
        isInstructor = instructor;
    }
}
