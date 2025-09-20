package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.dto.response.LessonsCreatonResponse;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.ModuleRepository;
import com.udemine.course_manage.service.Services.FileStorageService;
import com.udemine.course_manage.service.Services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class LessonsServiceImps implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private FileStorageService fileStorageServiceImp;

    @Override
    public List<Lessons> getAllLessons(){

        return  lessonRepository.findAll();
    }

    @Override
    public Lessons createLessons(LessonsCreatonRequest request) {
        fileStorageServiceImp.save(request.getVideo_url()); // lưu file
        Lessons lessons = new Lessons();
        lessons.setTitle(request.getTitle());
        lessons.setVideoUrl(request.getVideo_url().getOriginalFilename()); // chính là tên file
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
        fileStorageServiceImp.save(request.getVideo_url());
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
