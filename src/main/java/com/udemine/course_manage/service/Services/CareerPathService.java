package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.response.CareerPathResponse;

import java.util.List;

public interface CareerPathService {
    List<CareerPathResponse> getCareerPathsByCourses(List<Integer> courseIds);
}
