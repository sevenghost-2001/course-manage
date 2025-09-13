package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.CourseCreationRequest;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.dto.response.InstructorResponse;
import com.udemine.course_manage.dto.response.LessonResponse;
import com.udemine.course_manage.dto.response.ModuleResponse;
import com.udemine.course_manage.entity.Category;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Enrollment;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.entity.Voucher;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.CategoryRepository;
import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseMapper {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileStorageService fileStorageService;
    public Course toCourse(CourseCreationRequest request) {
        Course course = new Course();
        if(request.getImage() != null && !request.getImage().isEmpty()){
            fileStorageService.save(request.getImage());
            course.setImage(request.getImage().getOriginalFilename());
        }
        course.setTitle(request.getTitle());
        course.setShortDescription(request.getShortDescription());
        course.setCost(request.getCost());
        course.setDiscountedCost(request.getDiscountedCost());
        Category category = categoryRepository.findById(request.getId_category())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        course.setCategory(category);
        // lưu video demo nếu có
        if (request.getVideoDemo() != null && !request.getVideoDemo().isEmpty()) {
            fileStorageService.save(request.getVideoDemo());
            course.setVideoDemo(request.getVideoDemo().getOriginalFilename());
        }
        return course;
    }

    public void updateCourse(Course course, CourseCreationRequest request) {
        if(request.getImage() != null && !request.getImage().isEmpty()){
            fileStorageService.save(request.getImage());
            course.setImage(request.getImage().getOriginalFilename());
        }
        course.setTitle(request.getTitle());
        course.setShortDescription(request.getShortDescription());
        course.setCost(request.getCost());
        course.setDiscountedCost(request.getDiscountedCost());
        Category category = categoryRepository.findById(request.getId_category())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        course.setCategory(category);
        // lưu video demo nếu có
        if (request.getVideoDemo() != null && !request.getVideoDemo().isEmpty()) {
            fileStorageService.save(request.getVideoDemo());
            course.setVideoDemo(request.getVideoDemo().getOriginalFilename());
        }
    }

    /*
    //số điểm trung bình của khóa học
    double averageRating;
    //số người đã đánh giá khóa học
    int totalRatings;
    //Tên giảng viên khóa học
    String instructorName;
}
     */
    public CourseResponse toCourseResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setShortDescription(course.getShortDescription());
        response.setCost(course.getCost());
        response.setDiscountedCost(course.getDiscountedCost());
        response.setImage(course.getImage());
        response.setNameCategory(course.getCategory().getName());
        response.setAverageRating(course.getAverageRating() != null ? course.getAverageRating() : BigDecimal.ZERO);
        response.setTotalRatings((int) course.getReviews().stream().count());
        response.setCreated_at(course.getCreated_at());
        response.setInstructorName(String.valueOf(course.getTeaches()
                .stream()
                .map(teaches -> teaches.getInstructor().getName())
                .collect(Collectors.toList())));
        if (response.getInstructorName().isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_INSTRUCTOR);
        }
        response.setTotalEnrollments((int) course.getReviews().stream()
                .map(user -> user.getUser())
                .distinct().count());
//        lấy ra voucher thông qua enrollments và set trực tiên vào response
        response.setVoucher(null);
        List<Enrollment> enrollments = course.getEnrollments();
        if(enrollments != null && !enrollments.isEmpty()){
            String code = enrollments.stream()
                        .filter(en -> en.getVoucher() != null)
                        .map(en -> en.getVoucher().getCode())
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse("NO_VOUCHER");
            response.setVoucher(code);
        }
        //Lấy ra danh sách các lesson trong module
        List<LessonResponse> lessonResponseList = course.getModules().stream()
                .flatMap(module -> module.getLessons().stream())
                .map(lesson -> {
                    LessonResponse lessonResponse = new LessonResponse();
                    lessonResponse.setTitle(lesson.getTitle());
                    lessonResponse.setDuration(lesson.getDuration());
                    lessonResponse.setVideoUrl(lesson.getVideoUrl());
                    lessonResponse.setWatchDuration(lesson.getWatchDuration());
                    lessonResponse.setCompleted_at(lesson.getCompleted_at());
                    return lessonResponse;
                })
                .collect(Collectors.toList());
        // Lấy ra danh sách các modules trong khóa học
        List<ModuleResponse> moduleResponses = course.getModules().stream()
                .map(module -> {
                    ModuleResponse moduleResponse = new ModuleResponse();
                    moduleResponse.setTitle(module.getTitle());
                    moduleResponse.setPosition(moduleResponse.getPosition());
                    moduleResponse.setCreated_at(module.getCreated_at());
                    moduleResponse.setLessons(lessonResponseList);
                    //Tổng thời gian của tất cả lesson trong module, tính bằng giờ
                    double totalTimeLessons = module.getLessons().stream()
                            .mapToDouble(lesson -> lesson.getDuration() != 0 ? lesson.getDuration() : 0)
                            .sum();
                    moduleResponse.setTotalTimeLessons(totalTimeLessons);
                    return moduleResponse;
                })
                .collect(Collectors.toList());
        response.setModules(moduleResponses);
        //Thêm thông tin của giảng viên
        List<InstructorResponse> instructorResponses = course.getTeaches().stream()
                .map(teaches -> {
                    InstructorResponse instructorResponse = new InstructorResponse();
                    instructorResponse.setName(teaches.getInstructor().getName());
                    instructorResponse.setBiography(teaches.getInstructor().getBiography());
                    instructorResponse.setTotalCourses(teaches.getInstructor().getTeaches().size());
                    instructorResponse.setTotalStudents(
                            (int)teaches.getInstructor()
                                    .getTeaches()
                                    .stream()
                                    .flatMap(t -> t.getCourse().getEnrollments()
                                            .stream()
                                            .map(enrollment -> enrollment.getUser()))
                                    .distinct()
                                    .count()
                    );
                    return instructorResponse;
                })
                .collect(Collectors.toList());
        response.setInstructors(instructorResponses);
        response.setVideoDemo(course.getVideoDemo());
        //Tổng thời gian của tất cả các module trong khóa học, tính bằng giờ
        double totalTime = course.getModules().stream()
                .mapToDouble(Module::getTotalTimeLessons)
                .sum();
        response.setTotalTimeModules(totalTime);
        // Lấy ra tổng thời gian của tất cả các bài học trong khóa học, tính bằng giờ
        double totalLessonTime = course.getModules().stream()
                .flatMap(module -> module.getLessons().stream())
                .mapToDouble(lesson -> lesson.getDuration() != 0 ? lesson.getDuration() : 0)
                .sum();
        response.setTotalTimeModules(totalLessonTime);
        return response;
    }
}
