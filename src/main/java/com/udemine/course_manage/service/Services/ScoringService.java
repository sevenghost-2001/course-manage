package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.ScoringCreationRequest;
import com.udemine.course_manage.entity.Scoring;

import java.util.List;

public interface ScoringService {
    List<Scoring> getAllScorings();
    Scoring createScoring(ScoringCreationRequest request);
    Scoring updateScoring(int id, ScoringCreationRequest request);
    void deleteScoring(int id);
}
