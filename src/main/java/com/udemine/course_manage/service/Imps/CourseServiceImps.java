package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.dto.response.CategoryResponse;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.dto.response.HomePageResponse;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CareerPathMapper;
import com.udemine.course_manage.mapper.CourseMapper;
import com.udemine.course_manage.repository.*;
import com.udemine.course_manage.service.Services.CourseService;
import com.udemine.course_manage.service.Services.FileStorageService;
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
    @Override
    @Transactional
    public Course createCourse(CourseCreationRequest request, MultipartFile[] lessonVideos) {
        log.info("Received lessonVideos in service: {}", (lessonVideos != null ? Arrays.toString(lessonVideos) : "null"));

        // Kiểm tra null trước khi sử dụng
        if (lessonVideos == null) {
            log.warn("No lesson videos provided");
            lessonVideos = new MultipartFile[0]; // Khởi tạo mảng rỗng để tránh NullPointerException
        }

        Course course = new Course();
        if (request.getTitle() != null && courseRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.TITLE_EXISTED);
        }
        course = courseMapper.toCourse(request);
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            course.setImage(request.getImage().getOriginalFilename());
            fileStorageService.save(request.getImage());
        }
        if (request.getVideoDemo() != null && !request.getVideoDemo().isEmpty()) {
            course.setImgVideoDemo(request.getVideoDemo().getOriginalFilename());
            fileStorageService.save(request.getVideoDemo());
        }
        Course savedCourse = courseRepository.save(course);
        log.info("course: {}", savedCourse);

        if (request.getModules() != null && !request.getModules().isEmpty()) {
            int videoIndex = 0;
            for (ModuleCreationRequest moduleReq : request.getModules()) {
                if (moduleRepository.existsByTitle(moduleReq.getTitle())) {
                    throw new AppException(ErrorCode.TITLE_EXISTED);
                }
                int maxPosition = moduleRepository.findMaxPositionByCourseId(savedCourse.getId())
                        .orElse(0);
                Module module = new Module();
                module.setTitle(moduleReq.getTitle());
                module.setPosition(maxPosition + 1);
                module.setCourse(savedCourse);
                log.info("module: {}", module);
                Module savedModule = moduleRepository.save(module);

                if (moduleReq.getLessons() != null && !moduleReq.getLessons().isEmpty()) {
                    for (LessonsCreatonRequest lessonReq : moduleReq.getLessons()) {
                        Lessons lesson = new Lessons();
                        lesson.setTitle(lessonReq.getTitle());
                        lesson.setDuration((int) lessonReq.getDuration());
                        lesson.setWatchDuration((int) lessonReq.getWatch_duration());
                        lesson.setModule(savedModule);
                        log.info("lesson before save: {}", lesson);

                        // Kiểm tra và gán video
                        if (videoIndex < lessonVideos.length && lessonVideos[videoIndex] != null && !lessonVideos[videoIndex].isEmpty()) {
                            log.info("Saving video for lesson: {}, file: {}", lessonReq.getTitle(), lessonVideos[videoIndex].getOriginalFilename());
                            try {
                                fileStorageService.save(lessonVideos[videoIndex]);
                                lesson.setVideoUrl(lessonVideos[videoIndex].getOriginalFilename());
                                videoIndex++;
                            } catch (Exception e) {
                                log.error("Error saving file for lesson {}: {}", lessonReq.getTitle(), e.getMessage());
                                throw new AppException(ErrorCode.FILE_SAVE_FAILED);
                            }
                        } else {
                            log.warn("No video file provided for lesson: {}", lessonReq.getTitle());
                        }

                        lessonRepository.save(lesson);
                        log.info("lesson after save: {}", lesson);
                    }
                }
            }
        }
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
