package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.LateSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LateSubmissionRepository extends JpaRepository<LateSubmission,Integer> {
}
