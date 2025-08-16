package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.CareerPathRequest;
import com.udemine.course_manage.dto.response.CareerPathResponse;
import com.udemine.course_manage.entity.CareerPath;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.CareerPathMapper;
import com.udemine.course_manage.repository.CareerPathRepository;
import com.udemine.course_manage.repository.CareerToCourseRepository;
import com.udemine.course_manage.service.Services.CareerPathService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerPathServiceImps implements CareerPathService {
    @Autowired
    private CareerPathRepository careerPathRepository;
    @Autowired
    private CareerPathMapper careerPathMapper;
    @Autowired
    private CareerToCourseRepository careerToCourseRepository;
    @Override
    public List<CareerPathResponse> getCareerPathsByCourses(List<Integer> courseIds) {
        List<CareerPath> careerPaths = careerPathRepository.findCareerPathsContainingAllCourses(courseIds,courseIds.size());
        return careerPaths.stream().map(careerPath -> careerPathMapper.toResponse(careerPath)).toList();
    }

    @Override
    public CareerPathResponse createCareerPath(CareerPathRequest request) {
        if (careerPathRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CAREER_PATH_NAME_EXISTED);
        }

        //Thêm các khóa học vào CareerPath nếu có
        /*
        B1:
         */
        CareerPath careerPath = careerPathMapper.toCareerPath(request);
        return careerPathMapper.toResponse(careerPath);
    }

    @Override
    @Transactional
    public CareerPathResponse updateCareerPath(Integer id, CareerPathRequest request) {
        CareerPath careerPath = careerPathRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAREER_PATH_NOT_FOUND));
        // Xóa tất cả course cũ
        careerToCourseRepository.deleteByCareerPathId(id);
        //Cập nhật thông tin CareerPath
        careerPathMapper.updateCareerPathFromRequest(careerPath, request);
        CareerPathResponse careerPathResponse = careerPathMapper.toResponse(careerPath);
        return careerPathResponse;
    }
}
