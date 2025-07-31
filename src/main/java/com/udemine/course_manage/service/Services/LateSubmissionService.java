package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LateSubmissionCreationRequest;
import com.udemine.course_manage.entity.LateSubmission;

import java.util.List;

public interface LateSubmissionService {
    List<LateSubmission> getAllLateSubmissions();
    LateSubmission createLateSubmission(LateSubmissionCreationRequest request);
    LateSubmission updateLateSubmission(int id, LateSubmissionCreationRequest request);
    void deleteLateSubmission(int id);
}
