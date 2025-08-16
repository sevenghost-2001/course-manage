package com.udemine.course_manage.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CourseIdRequest {
    List<Integer> courseIds;
}
