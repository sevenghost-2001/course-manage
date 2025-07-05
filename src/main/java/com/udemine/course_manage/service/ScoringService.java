package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.ScoringCreationRequest;
import com.udemine.course_manage.entity.Scoring;
import com.udemine.course_manage.entity.Submission;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.ScoringRepository;
import com.udemine.course_manage.repository.SubmissionRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringService {
    @Autowired
    private ScoringRepository scoringRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Scoring> getAllScorings() {
        return scoringRepository.findAll();
    }

    public Scoring createScoring(ScoringCreationRequest request) {
        if (scoringRepository.existsBySubmissionIdAndUserId(request.getId_submission(), request.getId_instructor())) {
            throw new AppException(ErrorCode.SCORING_EXISTED);
        }
        Scoring scoring = new Scoring();
        scoring.setGrade(request.getGrade());
        scoring.setFeedback(request.getFeedback());

        Submission submission = submissionRepository.findById(request.getId_submission())
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        scoring.setSubmission(submission);

        User user = userRepository.findById(request.getId_instructor())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(!user.getIsInstructor()) {
            throw new AppException(ErrorCode.USER_NOT_INSTRUCTOR);
        }
        scoring.setUser(user);
        return scoringRepository.save(scoring);
    }

    public Scoring updateScoring(int id, ScoringCreationRequest request) {
        Scoring scoring = scoringRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCORING_NOT_FOUND));
        scoring.setGrade(request.getGrade());
        scoring.setFeedback(request.getFeedback());

        Submission submission = submissionRepository.findById(request.getId_submission())
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        scoring.setSubmission(submission);

        User user = userRepository.findById(request.getId_instructor())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(!user.getIsInstructor()) {
            throw new AppException(ErrorCode.USER_NOT_INSTRUCTOR);
        }
        scoring.setUser(user);
        return scoringRepository.save(scoring);
    }

    public void deleteScoring(int id) {
        if (!scoringRepository.existsById(id)) {
            throw new AppException(ErrorCode.SCORING_NOT_FOUND);
        }
        scoringRepository.deleteById(id);
    }
}
