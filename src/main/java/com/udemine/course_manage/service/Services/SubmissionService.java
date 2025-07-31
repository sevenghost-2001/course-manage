package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.SubmissionCreationRequest;
import com.udemine.course_manage.entity.Submission;

import java.util.List;

public interface SubmissionService {
    Submission createSubmission(SubmissionCreationRequest request);
    Submission updateSubmission(int id, SubmissionCreationRequest request);
    void deleteSubmission(int id);
    List<Submission> getAllSubmissions();
}
