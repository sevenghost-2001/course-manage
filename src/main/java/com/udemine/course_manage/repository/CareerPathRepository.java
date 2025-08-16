package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.CareerPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerPathRepository extends JpaRepository<CareerPath, Integer> {
    @Query("""
            SELECT DISTINCT cp
            FROM CareerPath cp
            JOIN cp.careerToCourses ctc
            WHERE ctc.course.id IN :courseIds
            GROUP BY cp.id
            HAVING COUNT(DISTINCT ctc.course.id) = :courseCount
            """)
    List<CareerPath> findCareerPathsContainingAllCourses(
            @Param("courseIds") List<Integer> courseIds,
            @Param("courseCount") long courseCount);
    Boolean existsByName(String name);
}
