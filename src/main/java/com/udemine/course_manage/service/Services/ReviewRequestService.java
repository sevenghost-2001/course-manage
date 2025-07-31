package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.ReviewRequestCreationRequest;
import com.udemine.course_manage.entity.ReviewRequest;

import java.util.List;

public interface ReviewRequestService {
    List<ReviewRequest> getAllReviewRequests();
    ReviewRequest createReviewRequest(ReviewRequestCreationRequest request);
    ReviewRequest updateReviewRequest(int id, ReviewRequestCreationRequest request);
    void deleteReviewRequest(int id);
}
