package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.ReviewRequestCreationRequest;
import com.udemine.course_manage.entity.ReviewRequest;
import com.udemine.course_manage.entity.Submission;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.ReviewRequestRepository;
import com.udemine.course_manage.repository.SubmissionRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewRequestService {
    @Autowired
    private ReviewRequestRepository reviewRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubmissionRepository submissionRepository;

    public List<ReviewRequest> getAllReviewRequests() {
        return reviewRequestRepository.findAll();
    }

    public ReviewRequest createReviewRequest(ReviewRequestCreationRequest request) {
        ReviewRequest reviewRequest =  new ReviewRequest();
        reviewRequest.setReason(request.getReason());
        reviewRequest.setStatus(request.getStatus());
        //Lấy thời gian hiện tại
        reviewRequest.setRequestAt(LocalDateTime.now());

        reviewRequest.setResponseMessage(request.getResponseMessage());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        reviewRequest.setUser(user);

        Submission submission = submissionRepository.findById(request.getId_submission())
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        reviewRequest.setSubmission(submission);

        return reviewRequestRepository.save(reviewRequest);
    }

    public ReviewRequest updateReviewRequest(int id, ReviewRequestCreationRequest request) {
        ReviewRequest reviewRequest = reviewRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_REQUEST_NOT_FOUND));

        reviewRequest.setReason(request.getReason());
        reviewRequest.setStatus(request.getStatus());
        reviewRequest.setResponseMessage(request.getResponseMessage());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        reviewRequest.setUser(user);

        Submission submission = submissionRepository.findById(request.getId_submission())
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        reviewRequest.setSubmission(submission);
        return reviewRequestRepository.save(reviewRequest);
    }

    public void deleteReviewRequest(int id) {
        if (!reviewRequestRepository.existsById(id)) {
            throw new AppException(ErrorCode.REVIEW_REQUEST_NOT_FOUND);
        }
        reviewRequestRepository.deleteById(id);
    }
}
