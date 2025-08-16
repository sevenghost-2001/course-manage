package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    boolean existsByTitle(String title);
    @Query(value = """
    SELECT c.* 
    FROM courses c
    LEFT JOIN enrollments e ON c.id = e.id_course
    GROUP BY c.id
    ORDER BY COUNT(e.id) DESC
    LIMIT 8
    """, nativeQuery = true)
    List<Course> findTop8ByOrderByCostDesc();
    List<Course> findTop8ByOrderByIdDesc();
    List<Course> findTop5ByCategoryId(Integer categoryId);
}
