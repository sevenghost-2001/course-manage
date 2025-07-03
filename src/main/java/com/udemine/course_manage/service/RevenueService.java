package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.RevenueCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Revenue;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.RevenueRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueService {
    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }

    public Revenue createRevenue(RevenueCreationRequest request) {
        //Không được trùng cặp user và course
        if (revenueRepository.existsByUserIdAndCourseId(request.getId_user(), request.getId_course())) {
            throw new AppException(ErrorCode.REVENUE_EXISTED);
        }
        Revenue revenue = new Revenue();
        revenue.setTotalEnrollments(request.getTotalEnrollments());
        revenue.setGrossIncome(request.getGrossIncome());
        revenue.setPlatformFeePercent(request.getPlatformFeePercent());
        revenue.setInstructorEarning(request.getInstructorEarning());
        // Xử lý khóa ngoại
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        revenue.setUser(user);

        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        revenue.setCourse(course);
        return revenueRepository.save(revenue);
    }

    public Revenue updateRevenue(int id, RevenueCreationRequest request) {
        // Kiểm tra xem revenue có tồn tại không
        Revenue revenue = revenueRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVENUE_NOT_FOUND));

        // Cập nhật các trường
        revenue.setTotalEnrollments(request.getTotalEnrollments());
        revenue.setGrossIncome(request.getGrossIncome());
        revenue.setPlatformFeePercent(request.getPlatformFeePercent());
        revenue.setInstructorEarning(request.getInstructorEarning());

        // Xử lý khóa ngoại
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        revenue.setUser(user);

        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        revenue.setCourse(course);
        return revenueRepository.save(revenue);
    }

    public void deleteRevenue(int id) {
        if (!revenueRepository.existsById(id)) {
            throw new AppException(ErrorCode.REVENUE_NOT_FOUND);
        }
        revenueRepository.deleteById(id);
    }
}
