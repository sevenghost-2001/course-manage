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
@Data
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

//    @OneToMany(mappedBy = "user")
//    List<Teach> teaches;
    @OneToMany(mappedBy = "user")
    List<UserRole> userRoles;

    @OneToMany(mappedBy = "user")
    List<Enrollment> enrollments;

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
