package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Status;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(CourseCreationRequest request){
        Course course = new Course();

        if(courseRepository.existsBytitle(request.getTitle())){
            throw new AppException(ErrorCode.TITLE_EXISTED);
        }

        course.setCourse_id(request.getCategory_id());
        course.setDescription(request.getDescription());
        course.setCategory_id(request.getCategory_id());
        course.setInstructor_id(request.getInstructor_id());
        course.setPrice(request.getPrice());
        course.setStatus(Status.valueOf(request.getStatus().toString()));
        course.setReview_notes(request.getReview_notes());
        course.onCreate();

        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id,CourseCreationRequest request){
        Optional<Course> existingCourseOpt = courseRepository.findById(id);
        if(existingCourseOpt.isEmpty()){
            throw new RuntimeException("Course not found with by "+id);
        }
        Course course = existingCourseOpt.get();
        course.setCourse_id(request.getCategory_id());
        course.setDescription(request.getDescription());
        course.setCategory_id(request.getCategory_id());
        course.setInstructor_id(request.getInstructor_id());
        course.setPrice(request.getPrice());
        course.setStatus(Status.valueOf(request.getStatus().toString()));
        course.setReview_notes(request.getReview_notes());

        return courseRepository.save(course);
    }

    public void deleteCourse(Integer id){
        if (!courseRepository.existsById(id)){
            throw new RuntimeException("Course not found with by "+id);
        }
        courseRepository.deleteById(id);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
}
