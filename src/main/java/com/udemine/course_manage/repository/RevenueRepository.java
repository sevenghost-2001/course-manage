package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    boolean existsByUserIdAndCourseId(int userId, int courseId);
}
