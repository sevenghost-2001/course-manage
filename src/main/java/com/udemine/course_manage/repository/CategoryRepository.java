package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByname(String name);
}
