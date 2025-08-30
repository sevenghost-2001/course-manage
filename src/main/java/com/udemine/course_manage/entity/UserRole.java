package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_role")
@Getter
@Setter// giúp minh tự động sinh ra getter, setter, toString, equals, hashCode
@NoArgsConstructor //tạo hàm khởi tạo không tham số
@AllArgsConstructor //tạo hàm khởi tạo có tất cả tham số
@Builder// tạo hàm khởi tạo mà khong cần truyền tất cả tham số
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) //đặt mức độ truy cập cho các trường
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    @JsonIgnore
    Role role;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    User user;

    @JsonProperty("Name of User")
    public String getNameUser() {
        return user != null ? user.getName() : null;
    }

    @JsonProperty("Name of Role")
    public String getNameRole() {
        return role != null ? role.getName() : null;
    }






}

