package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.RevenueCreationRequest;
import com.udemine.course_manage.entity.Revenue;

import java.util.List;

public interface RevenueService {
    List<Revenue> getAllRevenues();
    Revenue createRevenue(RevenueCreationRequest request);
    Revenue updateRevenue(int id, RevenueCreationRequest request);
    void deleteRevenue(int id);

}
