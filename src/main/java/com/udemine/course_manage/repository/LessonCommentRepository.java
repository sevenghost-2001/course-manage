package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.LessonComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonCommentRepository extends JpaRepository<LessonComment, Integer> {
}
