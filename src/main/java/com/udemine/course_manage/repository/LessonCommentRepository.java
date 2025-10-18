package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.LessonsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonCommentRepository extends JpaRepository<LessonsComment, Integer> {
    List<LessonsComment> findByLesson_IdAndParentCommentIsNull(Integer lessonId);
}
