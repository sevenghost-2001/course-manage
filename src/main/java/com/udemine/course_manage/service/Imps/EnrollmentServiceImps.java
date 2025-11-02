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

import java.time.LocalDateTime;
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
        if (enrollmentRepository.existsByUserIdAndCourseId(request.getId_user(), request.getId_course())) {
            System.out.println("⚠️ User đã đăng ký khóa học, bỏ qua Enrollment");
            return enrollmentRepository.findByUserIdAndCourseId(request.getId_user(), request.getId_course()).orElse(null);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setPaymentMethod(request.getPaymentMethod());
        enrollment.setEnrollStatus(request.getEnrollStatus());
        enrollment.setProgressPercent(request.getProgressPercent());
        enrollment.setCertificated(request.isCertificated());
        enrollment.setUsed(false); // mặc định
        enrollment.setTimeExpired(LocalDateTime.now().plusMonths(6)); // ví dụ: 6 tháng hết hạn

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        enrollment.setUser(user);

        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        enrollment.setCourse(course);

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getEnrollmentsByUser(int userId) {
        return enrollmentRepository.findByUserId(userId);
    }


    @Override
    public Enrollment updateEnrollment(int id, EnrollmentCreationRequest request){
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if (enrollmentRepository.existsByUserIdAndCourseId(request.getId_user(), request.getId_course())) {
            throw new AppException(ErrorCode.USER_ALREADY_ENROLLED);
        }
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
