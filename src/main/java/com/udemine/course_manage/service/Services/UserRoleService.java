package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.UserRoleCreationRequest;
import com.udemine.course_manage.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getAllUserRoles();
    UserRole createUserRole(UserRoleCreationRequest request);
    UserRole updateUserRole(int id, UserRoleCreationRequest request);
    void deleteUserRole(int id);
}
