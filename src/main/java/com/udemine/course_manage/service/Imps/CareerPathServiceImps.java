package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.entity.CareerPath;
import com.udemine.course_manage.mapper.CareerPathMapper;
import com.udemine.course_manage.repository.CareerPathRepository;
import com.udemine.course_manage.service.Services.CareerPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerPathServiceImps implements CareerPathService {
    @Autowired
    private CareerPathRepository careerPathRepository;
    @Autowired
    private CareerPathMapper careerPathMapper;
    @Override
    public List<CareerPathResponse> getCareerPathsByCourses(List<Integer> courseIds) {
        List<CareerPath> careerPaths = careerPathRepository.findCareerPathsContainingAllCourses(courseIds,courseIds.size());
        return careerPaths.stream().map(careerPath -> careerPathMapper.toResponse(careerPath)).toList();
    }
}
