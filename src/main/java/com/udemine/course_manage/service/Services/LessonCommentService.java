package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonCommentCreationRequest;
import com.udemine.course_manage.entity.LessonsComment;

import java.util.List;

public interface LessonCommentService {
    List<LessonsComment> getAllLessonComments();
    LessonsComment createLessonComment(LessonCommentCreationRequest request);
    LessonsComment updateLessonComment(int id, LessonCommentCreationRequest request);
    void deleteLessonComment(int id);
}
