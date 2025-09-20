package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImps implements FileStorageService {
    @Value("${file.root-path}")
    private String rootPath;

    @Override
    public void save(MultipartFile file) {
        Path root = Paths.get(rootPath);
        try{
            if (!Files.exists(root)){
                Files.createDirectories(root);
            }
            //resolve: nối đường dẫn
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new AppException(ErrorCode.FILE_SAVE_ERROR);
        }
    }

    @Override
    public Resource loadFile(String filename) {
        Path root = Paths.get(rootPath);
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new AppException(ErrorCode.FILE_NOT_FOUND);
            }
        }catch (Exception e){
            throw new AppException(ErrorCode.FILE_LOAD_ERROR);
        }
    }
}