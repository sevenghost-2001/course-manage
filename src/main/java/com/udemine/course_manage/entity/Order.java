package com.udemine.course_manage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // tên bảng trong DB
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    User user;


    @Column(name = "total_price")
    double totalPrice;


    @Column(name = "status")
    String status;


    @Column(name = "created_at")
    LocalDateTime createdAt;
}
