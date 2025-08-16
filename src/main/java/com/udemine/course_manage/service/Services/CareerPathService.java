package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.CareerPathRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.entity.CareerPath;

import java.util.List;

public interface CareerPathService {
    List<CareerPathResponse> getCareerPathsByCourses(List<Integer> courseIds);
    CareerPathResponse createCareerPath(CareerPathRequest request);
    CareerPathResponse updateCareerPath(Integer id, CareerPathRequest request);
}
