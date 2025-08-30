package com.udemine.course_manage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "vouchers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Voucher {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int id;
    String code;
    String status; // "active", "used", "expired"
    int quantity;
    LocalDateTime time_start;
    LocalDateTime time_end;

    @OneToMany(mappedBy = "voucher")
    List<Enrollment> enrollments;
}
