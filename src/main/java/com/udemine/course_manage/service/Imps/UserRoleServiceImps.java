package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.UserRoleCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.entity.UserRole;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.RoleRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.repository.UserRoleRepository;
import com.udemine.course_manage.service.Services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImps implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole createUserRole(UserRoleCreationRequest request) {
        // Kiểm tra trùng user-role
        if (userRoleRepository.existsByUserIdAndRoleId(request.getId_user(), request.getId_role())) {
            throw new AppException(ErrorCode.USER_ROLE_EXISTED);
        }

        UserRole userRole = new UserRole();

        // Lấy role theo id_role
        Role role = roleRepository.findById(request.getId_role())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        userRole.setRole(role);

        // Lấy user theo id_user
        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRole.setUser(user);

        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole updateUserRole(int id, UserRoleCreationRequest request) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ROLE_NOT_FOUND));

        Role role = roleRepository.findById(request.getId_role())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        userRole.setRole(role);

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRole.setUser(user);

        return userRoleRepository.save(userRole);
    }

    @Override
    public void deleteUserRole(int id) {
        if (!userRoleRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_ROLE_NOT_FOUND);
        }
        userRoleRepository.deleteById(id);
    }
}


