package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.CourseDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDiscountRepository extends JpaRepository<CourseDiscount,Integer> {
    boolean existsByCode(String code);
}
