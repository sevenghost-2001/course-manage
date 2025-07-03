package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonsService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    public List<Lessons> getAllLessons(){
        return  lessonRepository.findAll();
    }
    public Lessons createLessons(LessonsCreatonRequest request){
        Lessons lessons = new Lessons();
        if(lessonRepository.existsByTitle(request.getTitle())){
            throw new AppException(ErrorCode.TITLE_EXISTED);
        }
        lessons.setTitle(request.getTitle());
        lessons.setVideoUrl(request.getVideo_url());
        lessons.setDuration(request.getDuration());
        lessons.setWatchDuration(request.getWatch_duration());
        //Xử lý thuộc tính khóa ngoại
        Module module = moduleRepository.findById(request.getId_module())
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_EXIST));
        lessons.setModule(module);
        return lessonRepository.save(lessons);
    }

    public Lessons updateLessons(int id, LessonsCreatonRequest request) {
        Optional<Lessons> optionalLessons = lessonRepository.findById(id);
        if (optionalLessons.isEmpty()) {
            throw new AppException((ErrorCode.LESSONS_NOT_EXIST));
        }
        Lessons lessons = optionalLessons.get();
        lessons.setTitle(request.getTitle());
        lessons.setVideoUrl(request.getVideo_url());
        lessons.setDuration(request.getDuration());
        lessons.setWatchDuration(request.getWatch_duration());
        Module module = moduleRepository.findById(request.getId_module()).
                orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessons.setModule(module);
        return lessonRepository.save(lessons);

    }
    public void deleteLesson(int id){
        if(!lessonRepository.existsById(id)){
            throw new AppException(ErrorCode.LESSONS_NOT_EXIST);
        }
        lessonRepository.deleteById(id);
    }


}
