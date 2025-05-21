package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    boolean existsBytitle(String title);
}
