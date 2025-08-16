package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.SubmissionCreationRequest;
import com.udemine.course_manage.entity.Exercise;
import com.udemine.course_manage.entity.Submission;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.ExerciseRepository;
import com.udemine.course_manage.repository.SubmissionRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImps implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public Submission createSubmission(SubmissionCreationRequest request) {
        if(submissionRepository.existsByFileUrl(request.getFileUrl())){
            throw new AppException(ErrorCode.SUBMISSION_EXISTED);
        }
        Submission submission = new Submission();
        submission.setFileUrl(request.getFileUrl());
        submission.setSubmittedAt(request.getSubmittedAt());
        submission.setStatus(request.getStatus());
        //Xử lý khóa ngoại
        Exercise exercise = exerciseRepository.findById(request.getId_exercise())
                .orElseThrow(() -> new AppException(ErrorCode.EXERCISE_NOT_FOUND));
        submission.setExercise(exercise);
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        submission.setUser(user);
        return submissionRepository.save(submission);
    }

    @Override
    public Submission updateSubmission(int id, SubmissionCreationRequest request) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        submission.setFileUrl(request.getFileUrl());
        submission.setSubmittedAt(request.getSubmittedAt());
        submission.setStatus(request.getStatus());
        //Xử lý khóa ngoại
        Exercise exercise = exerciseRepository.findById(request.getId_exercise())
                .orElseThrow(() -> new AppException(ErrorCode.EXERCISE_NOT_FOUND));
        submission.setExercise(exercise);
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        submission.setUser(user);
        return submissionRepository.save(submission);
    }

    @Override
    public void deleteSubmission(int id) {
        if (!submissionRepository.existsById(id)) {
            throw new AppException(ErrorCode.SUBMISSION_NOT_FOUND);
        }
        submissionRepository.deleteById(id);
    }
}
