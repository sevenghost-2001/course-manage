package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lessons, Integer> {
    boolean existsByTitle (String title);
    boolean existsByVideoUrl(String videoUrl);
    boolean existsByDuration(double duration);
    boolean existsByWatchDuration(double watch_duration);
}
