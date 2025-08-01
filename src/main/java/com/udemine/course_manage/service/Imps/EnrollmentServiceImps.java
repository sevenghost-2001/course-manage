package com.udemine.course_manage.service.Imps;


import com.udemine.course_manage.dto.request.EnrollmentCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Enrollment;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.EnrollmentRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImps implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository; // De xu ly data

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment createEnrollment(EnrollmentCreationRequest request) {
        //        enrollment.setProgressPercent(0);
//        boolean percent = request.getProgressPercent() == 100? true : false;
//        enrollment.setCertificated(percent);
        Enrollment enrollment = new Enrollment();
        enrollment.setPaymentMethod(request.getPaymentMethod());
        enrollment.setEnrollStatus(request.getEnrollStatus());
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

    @Override
    public Enrollment updateEnrollment(int id, EnrollmentCreationRequest request){
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isEmpty()){
            throw new AppException(ErrorCode.ENROLLMENT_NOT_FOUND);
        }
        Enrollment enrollment = optionalEnrollment.get();
        enrollment.setPaymentMethod(request.getPaymentMethod());
        enrollment.setEnrollStatus(request.getEnrollStatus());
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

    @Override
    public void deleteEnrollment(int id){
        if(!enrollmentRepository.existsById(id)){
            throw new AppException(ErrorCode.ENROLLMENT_NOT_FOUND);
        }
        enrollmentRepository.deleteById(id);
    }
}
