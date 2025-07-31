package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.LateSubmissionCreationRequest;
import com.udemine.course_manage.entity.LateSubmission;
import com.udemine.course_manage.entity.Submission;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LateSubmissionRepository;
import com.udemine.course_manage.repository.SubmissionRepository;
import com.udemine.course_manage.service.Services.LateSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LateSubmissionServiceImps implements LateSubmissionService {
    @Autowired
    private LateSubmissionRepository lateSubmissionRepository;
    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public List<LateSubmission> getAllLateSubmissions() {
        return lateSubmissionRepository.findAll();
    }

    @Override
    public LateSubmission createLateSubmission(LateSubmissionCreationRequest request) {
        LateSubmission lateSubmission = new LateSubmission();
        lateSubmission.setReason(request.getReason());
        Submission submission = submissionRepository.findById(request.getId_submission())
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        lateSubmission.setSubmission(submission);
        return lateSubmissionRepository.save(lateSubmission);
    }

    @Override
    public LateSubmission updateLateSubmission(int id, LateSubmissionCreationRequest request) {
        LateSubmission lateSubmission = lateSubmissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LATE_SUBMISSION_NOT_FOUND));
        lateSubmission.setReason(request.getReason());
        Submission submission = submissionRepository.findById(request.getId_submission())
                .orElseThrow(() -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));
        lateSubmission.setSubmission(submission);
        return lateSubmissionRepository.save(lateSubmission);
    }

    @Override
    public void deleteLateSubmission(int id) {
        if (!lateSubmissionRepository.existsById(id)) {
            throw new AppException(ErrorCode.LATE_SUBMISSION_NOT_FOUND);
        }
        lateSubmissionRepository.deleteById(id);
    }
}
