package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Integer> {
    boolean existsByTitle(String title);
    boolean existsByPosition(int position);
    @Query("SELECT MAX(m.position) FROM Module m WHERE m.course.id = :courseId")
    Optional<Integer> findMaxPositionByCourseId(int courseId);
}
