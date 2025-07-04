package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Integer> {
    boolean existsByFileUrl(String fileUrl);
}
