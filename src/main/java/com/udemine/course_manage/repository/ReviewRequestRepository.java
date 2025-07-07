package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.ReviewRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRequestRepository extends JpaRepository<ReviewRequest,Integer> {

}
