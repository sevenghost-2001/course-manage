package com.udemine.course_manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemine.course_manage.dto.request.*;
import com.udemine.course_manage.dto.response.CourseResponse;
import com.udemine.course_manage.dto.response.HomePageResponse;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CourseMapper;
import com.udemine.course_manage.service.Imps.CourseServiceImps;
import com.udemine.course_manage.service.Services.CourseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ApiResponse<List<CourseResponse>> getAllCourses() {
        ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getAllCourses());
        return apiResponse;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CourseResponse> createCourse(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "videoDemo", required = false) MultipartFile videoDemo,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "shortDescription") String shortDescription,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "cost") BigDecimal cost,
            @RequestParam(value = "discountedCost") BigDecimal discountedCost,
            @RequestParam(value = "id_category") int idCategory,
            @RequestParam(value = "isCertification") Boolean isCertification,
            @RequestParam(value = "modules", required = false) String modulesJson,
            @RequestPart(value = "video_url", required = false) List<MultipartFile> videoUrls,
            @RequestPart(value = "fileUrl", required = false) List<MultipartFile> fileUrls
    ) {
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        CourseCreationRequest request = new CourseCreationRequest();

        request.setTitle(title);
        request.setShortDescription(shortDescription);
        request.setDescription(description);
        request.setCost(cost);
        request.setDiscountedCost(discountedCost);
        request.setId_category(idCategory);
        request.setIsCertification(isCertification);
        request.setImage(image);
        request.setVideoDemo(videoDemo);
        request.setModulesJson(modulesJson);

        if (modulesJson != null && !modulesJson.isBlank()) {
            try {
                List<ModuleCreationRequest> modules = objectMapper.readValue(
                        modulesJson,
                        new TypeReference<List<ModuleCreationRequest>>() {}
                );
                // Gán videoUrls và fileUrls cho từng bài học trong modules
                int videoIndex = 0;
                int fileIndex = 0;
                for (ModuleCreationRequest module : modules) {
                    if (module.getLessons() != null) {
                        for (var lesson : module.getLessons()) {
                            if (videoUrls != null && videoIndex < videoUrls.size()) {
                                lesson.setVideo_url(videoUrls.get(videoIndex));
                                videoIndex++;
                            }
                            if(lesson.getResources() != null){
                                for (LessonsCreatonRequest lessonReq : module.getLessons()) {
//                                    if (videoIndex < videoUrls.size()) {
//                                        lessonReq.setVideo_url(videoUrls.get(videoIndex++)); // Gán video_url
//                                    }
                                    if (lesson.getResources() != null) {
                                        for (LessonResourceCreationRequest resource : lesson.getResources()) {
                                            if (fileIndex < fileUrls.size()) {
                                                resource.setFileUrl(fileUrls.get(fileIndex++)); // Gán fileUrl
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                request.setModules(modules);
            } catch (Exception e) {
                log.error("Parse modulesJson error: {}", e.getMessage());
                throw new AppException(ErrorCode.INVALID_INPUT);
            }
        }

        Course course = courseService.createCourse(request);
        CourseResponse courseResponse = courseMapper.toCourseResponse(course);
        apiResponse.setResult(courseResponse);
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<CourseResponse> updateCourse(@PathVariable Integer id, @RequestBody CourseCreationRequest request) {
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        Course course = courseService.updateCourse(id, request);
        CourseResponse courseResponse = courseMapper.toCourseResponse(course);
        apiResponse.setResult(courseResponse);
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteCourseDiscount(@PathVariable int id) {
        courseService.deleteCourse(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DELETE_DONE.getCode());
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

    @GetMapping("/homepage")
    ApiResponse<?> getHomepage(){
        ApiResponse<HomePageResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getHomePageData());
        return apiResponse;
    }

    @GetMapping("detail/{id}")
    ApiResponse<CourseResponse> getCourseDetail(@PathVariable Integer id) {
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        CourseResponse courseResponse = courseService.getCourseById(id);
        apiResponse.setResult(courseResponse);
        return apiResponse;
    }

}
