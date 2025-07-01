package com.udemine.course_manage.service;


import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Enrollment;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.EnrollmentRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository; // De xu ly data

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    public Enrollment createEnrollment(EnrollmentCreationRequest request) {
        Enrollment enrollment = new Enrollment();
        enrollment.setPaymentMethod(request.getPaymentMethod());
        enrollment.setEnrollStatus(request.getEnrollStatus());
//        enrollment.setProgressPercent(0);
//        boolean percent = request.getProgressPercent() == 100? true : false;
//        enrollment.setCertificated(percent);

        enrollment.setProgressPercent(request.getProgressPercent());
        enrollment.setCertificated(request.isCertificated());
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        enrollment.setUser(user);
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }
}
