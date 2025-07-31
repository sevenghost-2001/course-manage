package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.Course_DiscountCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.CourseDiscount;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CourseDiscountMapper;
import com.udemine.course_manage.repository.CourseDiscountRepository;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.service.Services.CourseDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseDiscountServiceImps implements CourseDiscountService {
    @Autowired
    private CourseDiscountRepository courseDiscountRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseDiscountMapper courseDiscountMapper;

    @Override
    public CourseDiscount createCourseDiscount(Course_DiscountCreationRequest request){
        CourseDiscount courseDiscount = new CourseDiscount();
        if(courseDiscountRepository.existsByCode(request.getCode())){
            throw new AppException(ErrorCode.CODE_EXISTED);
        }
        courseDiscount = courseDiscountMapper.toCourseDiscount(request);
        // Tìm course theo id_course và set thủ công
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        courseDiscount.setCourse(course);
        return courseDiscountRepository.save(courseDiscount);
    }

    @Override
    public CourseDiscount updateCouseDiscount(int id,Course_DiscountCreationRequest request){
        Optional<CourseDiscount> courseDiscountOptional = courseDiscountRepository.findById(id);
        if(courseDiscountOptional.isEmpty()){
            throw new AppException(ErrorCode.COURSE_DISCOUNT_NOT_FOUND);
        }
        CourseDiscount courseDiscount = courseDiscountOptional.get();
        courseDiscountMapper.updateCourseDiscount(courseDiscount,request);
        // Tìm Category theo id_category và set thủ công
        Course course = courseRepository.findById(request.getId_course())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        courseDiscount.setCourse(course);
        return courseDiscountRepository.save(courseDiscount);
    }

    @Override
    public void deleteCourseDiscount(int id){
        if(!courseDiscountRepository.existsById(id)){
            throw new AppException(ErrorCode.COURSE_DISCOUNT_NOT_FOUND);
        }
        courseDiscountRepository.deleteById(id);
    }

    @Override
    public List<CourseDiscount> getAllCourseDiscount(){
        return courseDiscountRepository.findAll();
    }

}
