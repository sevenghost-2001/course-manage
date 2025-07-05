package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.LessonCommentCreationRequest;
import com.udemine.course_manage.dto.request.LessonResourceCreationRequest;
import com.udemine.course_manage.entity.LessonComment;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonCommentRepository;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class LessonCommentService {
    @Autowired
    private LessonCommentRepository lessonCommentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public List<LessonComment> getAllLessonComments() {
        return lessonCommentRepository.findAll();
    }

    public LessonComment createLessonComment(LessonCommentCreationRequest request) {
        LessonComment lessonComment = new LessonComment();
        lessonComment.setContent(request.getContent());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonComment.setUser(user);

        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonComment.setLesson(lesson);
        return lessonCommentRepository.save(lessonComment);
    }

    public LessonComment updateLessonComment(int id, LessonCommentCreationRequest request) {
        LessonComment lessonComment = lessonCommentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));
        lessonComment.setContent(request.getContent());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonComment.setUser(user);
        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonComment.setLesson(lesson);
        return lessonCommentRepository.save(lessonComment);
    }

    public void deleteLessonComment(int id) {
        if (!lessonCommentRepository.existsById(id)) {
            throw new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND);
        }
        lessonCommentRepository.deleteById(id);
    }

}
