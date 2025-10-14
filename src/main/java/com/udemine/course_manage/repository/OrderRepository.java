package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
