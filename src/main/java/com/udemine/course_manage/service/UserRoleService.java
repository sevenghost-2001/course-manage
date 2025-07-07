package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.UserRoleCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.entity.UserRole;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.RoleRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }

    public UserRole createUserRole(UserRoleCreationRequest request){
        //Kiểm xem cặp dữ liệu id_user và id_role đã tồn tại hay chưa
        if(userRoleRepository.existsByUserIdAndRoleId(request.getId_user(), request.getId_role())){
            throw new AppException(ErrorCode.USER_ROLE_EXISTED);
        }
        UserRole userRole = new UserRole();
        Role role = roleRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        userRole.setRole(role);

        User user = userRepository.findById(request.getId_user())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRole.setUser(user);
        return userRoleRepository.save(userRole);
    }
}
