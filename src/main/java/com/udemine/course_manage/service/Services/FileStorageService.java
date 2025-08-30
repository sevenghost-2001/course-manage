package com.udemine.course_manage.service.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public void save(MultipartFile video);
    Resource looadFile(String filename);
}
