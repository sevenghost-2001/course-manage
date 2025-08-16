package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.CareerToCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerToCourseRepository extends JpaRepository<CareerToCourse, Integer> {
    @Modifying
    @Query("DELETE FROM CareerToCourse c WHERE c.careerPath.id = :careerPathId")
    void deleteByCareerPathId(@Param("careerPathId") int careerPathId);
}
