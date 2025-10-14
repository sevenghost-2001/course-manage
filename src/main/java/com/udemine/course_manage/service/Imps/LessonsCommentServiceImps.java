package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.LessonCommentCreationRequest;
import com.udemine.course_manage.dto.response.LessonsCommentCreationResponse;
import com.udemine.course_manage.entity.LessonsComment;
import com.udemine.course_manage.entity.Lessons;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.LessonCommentMapper;
import com.udemine.course_manage.mapper.LessonsMapper;

import com.udemine.course_manage.repository.LessonCommentRepository;
import com.udemine.course_manage.repository.LessonRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.LessonCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
public class LessonsCommentServiceImps implements LessonCommentService {
    @Autowired
    private LessonCommentRepository lessonCommentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonCommentMapper lessonCommentMapper;

    @Override
    public List<LessonsComment> getAllLessonComments() {
        return lessonCommentRepository.findAll();
    }

    @Override
    public List<LessonsCommentCreationResponse> getCommentsByLesson(Integer lessonId) {
        List<LessonsComment> rootComments = lessonCommentRepository.findByLesson_IdAndParentCommentIsNull(lessonId);
        if (rootComments.isEmpty()) {
            return Collections.emptyList();
        }
        return rootComments.stream()
                .map(lessonCommentMapper::toLessonCommentCreationResponse)
                .toList();
    }


    @Override
    public LessonsComment createLessonComment(LessonCommentCreationRequest request) {
        LessonsComment lessonComment = new LessonsComment();
        lessonComment.setContent(request.getContent());

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonComment.setUser(user);

        Lessons lesson = lessonRepository.findById(request.getId_lesson())
                .orElseThrow(() -> new AppException(ErrorCode.LESSONS_NOT_EXIST));
        lessonComment.setLesson(lesson);

        // nếu có id_parent thì set parentComment
        if (request.getId_parent() != null) {
            LessonsComment parent = lessonCommentRepository.findById(request.getId_parent())
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));
            lessonComment.setParentComment(parent);
        }

        return lessonCommentRepository.save(lessonComment);
    }



    @Override
    public LessonsComment updateLessonComment(int id, LessonCommentCreationRequest request) {
        LessonsComment lessonComment = lessonCommentRepository.findById(id)
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

    @Override
    public void deleteLessonComment(int id) {
        if (!lessonCommentRepository.existsById(id)) {
            throw new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND);
        }
        lessonCommentRepository.deleteById(id);
    }

}
