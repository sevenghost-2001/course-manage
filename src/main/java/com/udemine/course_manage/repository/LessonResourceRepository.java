package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.LessonsResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonResourceRepository extends JpaRepository<LessonsResource,Integer> {
}
