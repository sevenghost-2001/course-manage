package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonResponse;
import com.udemine.course_manage.dto.response.LessonsCreatonResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.LessonsResponse;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.LessonsMapper;
import com.udemine.course_manage.mapper.ModuleMapper;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.ModuleRepository;
import com.udemine.course_manage.service.Services.FileStorageService;
import com.udemine.course_manage.service.Services.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LessonsServiceImps implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ModuleMapper moduleMapper;

    private FileStorageServiceImps fileStorageServiceImp;

    @Override
    public List<Lessons> getAllLessons(){

        return  lessonRepository.findAll();
    }

    @Override
    public LessonResponse toLessonResponse(Lessons lesson) {
        return lessonsMapper.toLessonResponse(lesson);
    }


    @Override
    public Lessons createLessons(LessonsCreatonRequest request) {
        if(request.getVideo_url() == null) {
            log.info("File video_url is null,update after");
        }else {
            fileStorageService.save(request.getVideo_url());
        }
        Lessons lessons = new Lessons();
        lessons.setTitle(request.getTitle());
        if(request.getVideo_url() == null){
            log.info("No update because file is null");
        }else{
            lessons.setVideoUrl(request.getVideo_url().getOriginalFilename());
        }
        lessons.setDuration(request.getDuration());
        lessons.setWatchDuration(request.getWatch_duration());
        Module module = moduleRepository.findById(request.getId_module())
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_EXIST));
        lessons.setModule(module);
        return lessonRepository.save(lessons);
    }

    @Override
    public Lessons updateLessons(int id, LessonsCreatonRequest request) {
        Optional<Lessons> optionalLessons = lessonRepository.findById(id);
        fileStorageService.save(request.getVideo_url());
        if (optionalLessons.isEmpty()) {
            throw new AppException((ErrorCode.LESSONS_NOT_EXIST));
        }
        Lessons lessons = optionalLessons.get();
        lessons.setTitle(request.getTitle());
        lessons.setVideoUrl(request.getVideo_url().getOriginalFilename());
        lessons.setDuration(request.getDuration());
        lessons.setWatchDuration(request.getWatch_duration());
        Module module = moduleRepository.findById(request.getId_module()).
                orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessons.setModule(module);
        return lessonRepository.save(lessons);
    }


    @Override
    public void deleteLesson(int id){
        if(!lessonRepository.existsById(id)){
            throw new AppException(ErrorCode.LESSONS_NOT_EXIST);
        }
        lessonRepository.deleteById(id);
    }


}
