package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.dto.response.*;
import com.udemine.course_manage.entity.*;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CareerPathMapper;
import com.udemine.course_manage.mapper.CourseMapper;
import com.udemine.course_manage.repository.*;
import com.udemine.course_manage.service.Services.CourseService;
import com.udemine.course_manage.service.Services.FileStorageService;
import com.udemine.course_manage.service.Services.LessonResourceService;
import com.udemine.course_manage.service.Services.LessonService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonResourceRepository lessonResourceRepository;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonResourceService lessonResourceService;
    private MultipartFile findFileByName(MultipartFile[] files, String filename) {
        return Arrays.stream(files)
                .filter(f -> f.getOriginalFilename().equals(filename))
                .findFirst()
                .orElse(null);
    }
    @Override
    @Transactional
    public Course createCourse(CourseCreationRequest request) {
        Course course = courseMapper.toCourse(request);
        log.info("requeset data: " + request);
        log.info("course mapped from request {}", course);
        Course savedCourse = courseRepository.save(course);

        List<Module> moduleList = new ArrayList<>();
        if (request.getModules() != null) {
            for (ModuleCreationRequest moduleReq : request.getModules()) {
                Module module = new Module();
                module.setTitle(moduleReq.getTitle());
                module.setPosition(moduleRepository.findMaxPositionByCourseId(savedCourse.getId()).orElse(0) + 1);
                module.setCourse(savedCourse);
                Module savedModule = moduleRepository.save(module);

                if (moduleReq.getLessons() != null) {
                    List<Lessons> lessonsList = new ArrayList<>();
                    for (LessonsCreatonRequest lessonReq : moduleReq.getLessons()) {
                        lessonReq.setId_module(savedModule.getId()); // Gán ID module
                        Lessons lesson = lessonService.createLessons(lessonReq);
                        if (lessonReq.getResources() != null) {
                           List<LessonsResource> resourcesList = new ArrayList<>();
                            for (LessonResourceCreationRequest resourceReq : lessonReq.getResources()) {
                                resourceReq.setId_lesson(lesson.getId()); // Gán ID lesson
                                LessonsResource resource = lessonResourceService.createLessonResource(resourceReq);
                                log.info("Created lesson resource with title: " + resourceReq.getTitle());
                                resourcesList.add(resource);
                            }
                            lesson.setResources(resourcesList);
                        }
                        lessonsList.add(lesson);
                    }
                    savedModule.setLessons(lessonsList);
                }
                moduleList.add(savedModule);
            }
        }
        savedCourse.setModules(moduleList);
        log.info("course after save id: {}", savedCourse.getId());
        return savedCourse;
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
        return courseRepository.findById(id)
                .map(course -> courseMapper.toCourseResponse(course))
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
    }

}
