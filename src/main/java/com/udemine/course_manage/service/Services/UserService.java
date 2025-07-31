package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    User createUser(UserCreationRequest request);
    User updateUser(Integer id, UserCreationRequest request);
    void deleteUser(Integer id);
    List<User> getAllUsers();
}
