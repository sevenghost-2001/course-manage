package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.RoleCreationRequest;
import com.udemine.course_manage.entity.Role;

import java.util.List;

public interface RoleService {
    Role createRole(RoleCreationRequest request);
    Role updateRole(Integer id, RoleCreationRequest request);
    void deleteRole(Integer id);
    List<Role> getAllRoles();
}
