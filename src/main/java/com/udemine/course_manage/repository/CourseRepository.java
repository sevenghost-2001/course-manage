package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    boolean existsByTitle(String title);
    List<Course> findTop8ByOrderByCostDesc();
    List<Course> findTop8ByOrderByIdDesc();
    List<Course> findTop5ByCategoryId(Integer categoryId);
}
