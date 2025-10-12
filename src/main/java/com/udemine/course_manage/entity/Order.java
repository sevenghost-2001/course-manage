package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int id;
    @JoinColumn(name = "id_user", nullable = false)
    @ManyToOne
    User user;
    double total_price;
    String status;//// PENDING, SUCCESS, FAILED
    LocalDateTime created_at;
}
