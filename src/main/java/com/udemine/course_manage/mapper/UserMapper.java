package com.udemine.course_manage.mapper;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.service.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    @Autowired
    private FileStorageService fileStorageService;
    public User toUser(UserCreationRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if(request.getAvatar() != null && !request.getAvatar().isEmpty()){
            fileStorageService.save(request.getAvatar());
            user.setAvatar(request.getAvatar().getOriginalFilename());
        }
        user.setBiography(request.getBiography());
        user.setLevels(request.getLevels());
        user.setRanks(request.getRanks());
        user.setUpdated_at(java.time.LocalDateTime.now());
        return user;
    }

    public void updateUser(User user, UserCreationRequest request){
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if(request.getAvatar() != null && !request.getAvatar().isEmpty()){
            fileStorageService.save(request.getAvatar());
            user.setAvatar(request.getAvatar().getOriginalFilename());
        }
        user.setBiography(request.getBiography());
        user.setLevels(request.getLevels());
        user.setRanks(request.getRanks());
        user.setUpdated_at(java.time.LocalDateTime.now());
    }

}
