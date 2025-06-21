package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.RoleCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.RoleMapper;
import com.udemine.course_manage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    public Role createRole(RoleCreationRequest request) {
//        Role role = new Role();
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        return roleRepository.save(role);
    }

    public Role updateRole(Integer id, RoleCreationRequest request) {
        Optional<Role> existingRoleOpt = roleRepository.findById(id); // Optional provides function findById() and get()
        if(existingRoleOpt.isEmpty()){
            throw new RuntimeException("Role not found with id: " + id);
        }

        Role role = existingRoleOpt.get();
//        roleMapper.updateRole(role, request);
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return roleRepository.save(role);
    }

    public void deleteRole(Integer id) {
        if(!roleRepository.existsById(id)){
            throw new RuntimeException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
