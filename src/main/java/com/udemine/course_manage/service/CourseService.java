package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Status;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CourseMapper;
import com.udemine.course_manage.repository.CategoryRepository;
import com.udemine.course_manage.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private CourseMapper courseMapper;

    public Course createCourse(CourseCreationRequest request){
        Course course = new Course();

        if(courseRepository.existsByTitle(request.getTitle())){
            throw new AppException(ErrorCode.TITLE_EXISTED);
        }

        course = courseMapper.toCourse(request);
        // Tìm Category theo id_category và set thủ công
        Category category = categoryRepository.findById(request.getId_category())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        course.setCategory(category);
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id,CourseCreationRequest request){
        Optional<Course> existingCourseOpt = courseRepository.findById(id);
        if(existingCourseOpt.isEmpty()){
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }
        Course course = existingCourseOpt.get();
        // Dùng MapStruct cập nhật các field đơn giản
          courseMapper.updateCourse(course,request);
        // Cập nhật category nếu id_category có thể thay đổi
        Category category = categoryRepository.findById(request.getId_category())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        course.setCategory(category);
        return courseRepository.save(course);
    }
    public void deleteCourse(Integer id){
        if (!courseRepository.existsById(id)){
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }
        courseRepository.deleteById(id);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
}
