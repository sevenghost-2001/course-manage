package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.LessonsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonResponseRepository extends JpaRepository<LessonsResponse, Integer> {
}
