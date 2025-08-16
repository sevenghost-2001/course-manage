package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.entity.CareerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareerPathMapper {
    @Autowired
    private CourseMapper courseMapper;
    public CareerPathResponse toResponse(CareerPath careerPath){
        if (careerPath == null) {
            return null;
        }
        return CareerPathResponse.builder()
                .id(careerPath.getId())
                .name(careerPath.getName())
                .description(careerPath.getDescription())
                .image(careerPath.getImage())
                .courses(careerPath.getCarreerToCourses().stream().map(ctc ->
                        courseMapper.toCourseResponse(ctc.getCourse())).toList())
                .build();
    }
}
