package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImp implements FileStorageService {
    @Value("${file.root-path}")
    private String rootPath;


    @Override
    public void save(MultipartFile video) {
        Path rootFolderPath = Paths.get(rootPath);
        try {
            if(!Files.exists(rootFolderPath)) {
                Files.createDirectories(rootFolderPath);
            }
            //rosolve đại diện cho dấu /
            Files.copy(video.getInputStream(),rootFolderPath.resolve(video.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not create root folder: " + e.getMessage());
        }
    }

    @Override
    public Resource looadFile(String filename) {

        try {
            Path rootFolderPath = Paths.get(rootPath);
            Path video = rootFolderPath.resolve(filename);
            Resource resource = new UrlResource(video.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }
}
