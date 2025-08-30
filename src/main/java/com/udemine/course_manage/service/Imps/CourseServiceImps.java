package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.dto.response.CategoryResponse;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.dto.response.HomePageResponse;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CareerPathMapper;
import com.udemine.course_manage.mapper.CourseMapper;
import com.udemine.course_manage.repository.CareerPathRepository;
import com.udemine.course_manage.repository.CategoryRepository;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.service.Services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImps implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CareerPathRepository careerPathRepository;
    @Autowired
    private CareerPathMapper careerPathMapper;
    @Override
    @Transactional
    public Course createCourse(CourseCreationRequest request){
        Course course = new Course();
        if(courseRepository.existsByTitle(request.getTitle())){
            throw new AppException(ErrorCode.TITLE_EXISTED);
        }
        course = courseMapper.toCourse(request);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Integer id,CourseCreationRequest request){
        Optional<Course> existingCourseOpt = courseRepository.findById(id);
        if(existingCourseOpt.isEmpty()){
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }
        Course course = existingCourseOpt.get();
        courseMapper.updateCourse(course,request);
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Integer id){
        if (!courseRepository.existsById(id)){
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponse> getAllCourses(){
        return courseRepository.findAll().stream().map(course -> courseMapper.toCourseResponse(course)).toList();
    }

    @Override
    public HomePageResponse getHomePageData() {
        List<CareerPathResponse> listCareerPath = careerPathRepository.findAll().stream()
                .map(careerPath -> careerPathMapper.toResponse(careerPath)) .toList();
        List<CourseResponse> popular = courseRepository.findTop8ByOrderByCostDesc().stream()
                .map(course -> courseMapper.toCourseResponse(course)).toList(); // ví dụ: chi phí cao là phổ biến
        List<CourseResponse> newest = courseRepository.findTop8ByOrderByIdDesc().stream()
                .map(course -> courseMapper.toCourseResponse(course)).toList(); // id cao hơn là mới
        List<Category> allCategories = categoryRepository.findAll();

        Map<String, List<CourseResponse>> byCategory = new HashMap<>();
        for (Category category : allCategories){
            List<CourseResponse> list = courseRepository.findTop5ByCategoryId(category.getId()).stream()
                    .map(course -> courseMapper.toCourseResponse(course)).toList();
            byCategory.put(category.getName(),list);

        }
        return HomePageResponse.builder()
                .careerPaths(listCareerPath)
                .popularCourses(popular)
                .newestCourses(newest)
                .coursesByCategory(byCategory)
                .build();
    }

    @Override
    public CourseResponse getCourseById(Integer id) {
        return null;
    }

}
