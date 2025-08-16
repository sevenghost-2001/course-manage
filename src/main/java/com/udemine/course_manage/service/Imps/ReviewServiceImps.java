package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.ReviewCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Review;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.ReviewMapper;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.ReviewRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImps implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    @Override
    public Review createReview(ReviewCreationRequest request){
        Review review = reviewMapper.toReview(request);
        //Xử lý thuộc tính khóa ngoại
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        review.setUser(user);
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        review.setCourse(course);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(int id,ReviewCreationRequest request){
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if(optionalReview.isEmpty()){
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        Review review = optionalReview.get();
        reviewMapper.updateReview(review,request);
        //Xử lý thuộc tính khóa ngoại
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        review.setUser(user);
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        review.setCourse(course);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(int id){
        if(!reviewRepository.existsById(id)){
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(id);
    }
}
