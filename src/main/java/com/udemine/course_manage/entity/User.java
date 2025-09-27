package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
    @Column(name = "fullname", nullable = false)
     String name;
    String avatar;
    @Column(nullable = false, unique = true)
     String email;
    @Column(name = "passwords", nullable = false)
     String password;
    @Column(name = "is_instructor", nullable = false)
     Boolean isInstructor = false;

    @Column(updatable = false)
     LocalDateTime updated_at;
    String biography;
    int ranks;
    int levels;

    // Login attempts failed
    @Column(name = "failed_attempt")
    int failedAttempts;
    @Column(name = "account_non_locked",nullable = false)
    boolean accountNonLocked = true;
    @Column(name = "lock_time")
    LocalDateTime lockTime;
    @Column(name = "lockout_count")
    int lockoutCount;



    @OneToMany(mappedBy = "user", orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonIgnore
    List<UserRole> userRoles;

    @OneToMany(mappedBy = "user")
    List<Enrollment> enrollments;

    @OneToMany(mappedBy = "instructor")
    List<Teach> teaches;

    @OneToOne(mappedBy = "user")
    ForgotPassword forgotPassword;

    @JsonProperty("Roles")
    public List<String> getRoles() {
        return userRoles != null ? userRoles.stream().map(UserRole::getNameRole).toList() : null;
    }


    @PrePersist
    public void onCreate(){
        this.updated_at = LocalDateTime.now();
    }
    //Chuyển Entity sang DTO
}
