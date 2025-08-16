package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.RevenueCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Revenue;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.RevenueRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueServiceImps implements RevenueService {
    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }

    @Override
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

    @Override
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

    @Override
    public void deleteRevenue(int id) {
        if (!revenueRepository.existsById(id)) {
            throw new AppException(ErrorCode.REVENUE_NOT_FOUND);
        }
        revenueRepository.deleteById(id);
    }
}
