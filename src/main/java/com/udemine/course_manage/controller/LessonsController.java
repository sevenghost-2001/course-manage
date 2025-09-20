package com.udemine.course_manage.controller;
import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonResponse;
import com.udemine.course_manage.dto.response.LessonsCreatonResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.LessonsMapper;
import com.udemine.course_manage.service.Services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonsController {

    @Autowired
    private LessonService lessonsService;
    @Autowired
    private LessonsMapper lessonsMapper;

    @GetMapping
    ApiResponse<List<LessonResponse>> getAllLessons(){
        ApiResponse<List<LessonResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(lessonsService.getAllLessons()
                .stream()
                .map(lesson -> lessonsMapper.toLessonResponse(lesson)) // hoặc sử dụng phương thức chuyển đổi phù hợp
                .collect(java.util.stream.Collectors.toList()));

        return  apiResponse;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Lessons> createLessons(
            LessonsCreatonRequest request

//            @RequestPart("video_url") MultipartFile video,
//            @RequestParam("title") String title,
//            @RequestParam("duration") double duration,
//            @RequestParam("watch_duration") double watchDuration,
//            @RequestParam("id_module") int idModule
    ) {
//        LessonsCreatonResponse response = LessonsCreatonResponse.builder()
//                .title(title)
//               .video_url(video.getOriginalFilename()) // lấy tên file
//                .duration(duration)
//                .watch_duration(watchDuration)
//                .id_module(idModule)
//                .build();

        Lessons lessons = lessonsService.createLessons(request);
        return ResponseEntity.ok(lessons);
    }




    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Lessons> updateLessons(
            LessonsCreatonRequest request
//            @PathVariable int id,
//            @RequestPart("video_url") MultipartFile file,
//            @RequestParam("title") String title,
//            @RequestParam("duration") double duration,
//            @RequestParam("watch_duration") double watchDuration,
//            @RequestParam("id_module") int idModule
    ) {
//        LessonsCreatonResponse response = LessonsCreatonResponse.builder()
//                .title(title)
//             //   .video_url(file.getOriginalFilename())
//                .duration(duration)
//                .watch_duration(watchDuration)
//                .id_module(idModule)
//                .build();

        Lessons lessons = lessonsService.updateLessons(request.getId(), request);
        return ResponseEntity.ok(lessons);
    }




    @DeleteMapping("/{id}")
    ApiResponse<String> deleteLessons(@PathVariable int id){
        lessonsService.deleteLesson(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(ErrorCode.DELETE_DONE.getMessage());
        return apiResponse;
    }

}
