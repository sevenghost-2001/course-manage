package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.LessonResponseCreationRequest;
import com.udemine.course_manage.entity.LessonsComment;
import com.udemine.course_manage.entity.LessonsResponse;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.LessonCommentRepository;
import com.udemine.course_manage.repository.LessonResponseRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.LessonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonsResponseServiceImps implements LessonResponseService {
    @Autowired
    private LessonResponseRepository lessonResponseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonCommentRepository lessonCommentRepository;

    @Override
    public List<LessonsResponse> getAllLessonResponses() {
        return lessonResponseRepository.findAll();
    }

    @Override
    public LessonsResponse createLessonResponse(LessonResponseCreationRequest request) {
        LessonsResponse lessonResponse = new LessonsResponse();
        lessonResponse.setContent(request.getContent());
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonResponse.setUser(user);

        LessonsComment lessonComment = lessonCommentRepository.findById(request.getLesson_comment_id())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));
        lessonResponse.setLessonComment(lessonComment);
        return lessonResponseRepository.save(lessonResponse);
    }

    @Override
    public LessonsResponse updateLessonResponse(int id, LessonResponseCreationRequest request) {
        Optional<LessonsResponse> optionalLessonResponse = lessonResponseRepository.findById(id);
        if (optionalLessonResponse.isEmpty()) {
            throw new AppException(ErrorCode.LESSON_RESPONSE_NOT_FOUND);
        }
        LessonsResponse lessonResponse = optionalLessonResponse.get();
        lessonResponse.setContent(request.getContent());
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        lessonResponse.setUser(user);

        LessonsComment lessonComment = lessonCommentRepository.findById(request.getLesson_comment_id())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_COMMENT_NOT_FOUND));
        lessonResponse.setLessonComment(lessonComment);
        return lessonResponseRepository.save(lessonResponse);
    }

    @Override
    public void deleteLessonResponse(int id) {
        if (!lessonResponseRepository.existsById(id)) {
            throw new AppException(ErrorCode.LESSON_RESPONSE_NOT_FOUND);
        }
        lessonResponseRepository.deleteById(id);
    }


}
