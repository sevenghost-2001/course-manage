package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.LessonsCreatonRequest;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.LessonsMapper;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LessonsService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LessonsMapper  lessonsMapper;
    public List<Lessons> getAllLessons(){
        return  lessonRepository.findAll();
    }
    public Lessons createLessons(LessonsCreatonRequest request){
        Lessons lessons = new Lessons();
        if(lessonRepository.existsByTitle(request.getTitle())){
            throw new AppException(ErrorCode.TITLE_EXISTED);
        }
        if(lessonRepository.existsByDuration(request.getDuration())){
            throw new AppException(ErrorCode.LESSONS_NOT_EXIST);
        }
        lessons = lessonsMapper.toLessons(request);

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
        lessonsMapper.updateLessons(lessons, request);
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
