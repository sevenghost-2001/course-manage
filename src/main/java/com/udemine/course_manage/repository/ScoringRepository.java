package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Scoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoringRepository extends JpaRepository<Scoring, Integer> {
    boolean existsBySubmissionIdAndUserId(int submissionId, int userId);
}
