package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    boolean existsByUserIdAndCourseId(int userId, int courseId);
}
