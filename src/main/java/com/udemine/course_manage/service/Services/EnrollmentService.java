package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();
    Enrollment createEnrollment(EnrollmentCreationRequest request);
    Enrollment updateEnrollment(int id, EnrollmentCreationRequest request);
    void deleteEnrollment(int id);
}
