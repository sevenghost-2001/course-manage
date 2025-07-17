package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.LessonResponseCreationRequest;
import com.udemine.course_manage.entity.LessonComment;
import com.udemine.course_manage.entity.LessonResponse;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonCommentRepository;
import com.udemine.course_manage.repository.LessonResponseRepository;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonResponseService {
    @Autowired
    private LessonResponseRepository lessonResponseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonCommentRepository lessonCommentRepository;


    public List<LessonResponse> getAllLessonResponses() {
        return lessonResponseRepository.findAll();
    }

    public LessonResponse createLessonResponse(LessonResponseCreationRequest request) {
        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setContent(request.getContent());
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonResponse.setUser(user);

        LessonComment lessonComment = lessonCommentRepository.findById(request.getLesson_comment_id())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));
        lessonResponse.setLessonComment(lessonComment);
        return lessonResponseRepository.save(lessonResponse);
    }

    public LessonResponse updateLessonResponse(int id, LessonResponseCreationRequest request) {
        Optional<LessonResponse> optionalLessonResponse = lessonResponseRepository.findById(id);
        if (optionalLessonResponse.isEmpty()) {
            throw new AppException(ErrorCode.LESSON_RESPONSE_NOT_FOUND);
        }
        LessonResponse lessonResponse = optionalLessonResponse.get();
        lessonResponse.setContent(request.getContent());
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonResponse.setUser(user);

        LessonComment lessonComment = lessonCommentRepository.findById(request.getLesson_comment_id())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));
        lessonResponse.setLessonComment(lessonComment);
        return lessonResponseRepository.save(lessonResponse);
    }

    public void deleteLessonResponse(int id) {
        if (!lessonResponseRepository.existsById(id)) {
            throw new AppException(ErrorCode.LESSON_RESPONSE_NOT_FOUND);
        }
        lessonResponseRepository.deleteById(id);
    }


}
