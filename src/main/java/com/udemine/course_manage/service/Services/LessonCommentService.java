package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.LessonCommentCreationRequest;
import com.udemine.course_manage.entity.LessonComment;

import java.util.List;

public interface LessonCommentService {
    List<LessonComment> getAllLessonComments();
    LessonComment createLessonComment(LessonCommentCreationRequest request);
    LessonComment updateLessonComment(int id, LessonCommentCreationRequest request);
    void deleteLessonComment(int id);
}
