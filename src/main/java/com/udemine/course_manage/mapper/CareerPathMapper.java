package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.CareerPathRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.entity.CareerPath;
import com.udemine.course_manage.entity.CareerToCourse;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CareerPathRepository;
import com.udemine.course_manage.repository.CareerToCourseRepository;
import com.udemine.course_manage.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerPathMapper {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CareerToCourseRepository careerToCourseRepository;
    @Autowired
    private CareerPathRepository careerPathRepository;
    public CareerPathResponse toResponse(CareerPath careerPath){
        if (careerPath == null) {
            return null;
        }
        return CareerPathResponse.builder()
                .id(careerPath.getId())
                .name(careerPath.getName())
                .description(careerPath.getDescription())
                .image(careerPath.getImage())
                .courses(careerPath.getCareerToCourses().stream().map(ctc ->
                        courseMapper.toCourseResponse(ctc.getCourse())).toList())
                .build();
    }
    public CareerPath toCareerPath(CareerPathRequest request){
        CareerPath careerPath = CareerPath.builder()
                .name(request.getName())
                .image(request.getImage())
                .description(request.getDescription())
                .build();
        List<CareerToCourse> careerToCourses = new ArrayList<>();
        for (int courseId : request.getCourseIds()) {
            if(courseRepository.existsById(courseId)){
                 CareerToCourse careerCourse = CareerToCourse.builder()
                        .careerPath(careerPath)
                        .course(courseRepository.findById(courseId).orElse(null))
                        .build();
                     careerToCourses.add(careerCourse);
            } else {
                throw new AppException(ErrorCode.COURSE_NOT_FOUND);
            }
        }
        careerPath.setCareerToCourses(careerToCourses);
        careerPathRepository.save(careerPath);
        for (CareerToCourse careerToCourse : careerToCourses) {
            careerToCourse.setCareerPath(careerPath);
            careerToCourseRepository.save(careerToCourse);
        }
        return careerPath;
    }

    public void updateCareerPathFromRequest (CareerPath careerPath, CareerPathRequest request) {
        if (careerPath == null) {
            throw new AppException(ErrorCode.CAREER_PATH_NOT_FOUND);
        }
        careerPath.setName(request.getName());
        careerPath.setDescription(request.getDescription());
        careerPath.setImage(request.getImage());

        List<CareerToCourse> careerToCourses = new ArrayList<>();
        for (int courseId : request.getCourseIds()) {
            if(courseRepository.existsById(courseId)){
                CareerToCourse careerCourse = CareerToCourse.builder()
                        .careerPath(careerPath)
                        .course(courseRepository.findById(courseId).orElse(null))
                        .build();
                careerToCourses.add(careerCourse);
            } else {
                throw new AppException(ErrorCode.COURSE_NOT_FOUND);
            }
        }
        careerPath.setCareerToCourses(careerToCourses);
        careerToCourseRepository.saveAll(careerToCourses);
        careerPathRepository.save(careerPath);
    }
}
