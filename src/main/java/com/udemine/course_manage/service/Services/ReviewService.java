package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.ReviewCreationRequest;
import com.udemine.course_manage.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    Review createReview(ReviewCreationRequest request);
    Review updateReview(int id,ReviewCreationRequest request);
    void deleteReview(int id);
}
