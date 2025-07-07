package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.dto.request.UserRoleCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.entity.UserRole;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.UserMapper;
import com.udemine.course_manage.repository.RoleRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.repository.UserRoleRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;

    public User createUser(UserCreationRequest request){
//        User user = new User();

        if(userRepository.existsByname(request.getName())){
            throw new  AppException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByemail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(request);
        // Strength/ thuật toán càng phức tạp thì sẽ càng làm chậm hệ thống , mặc định độ phức tạp sẽ là 10
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        //Gọi hàm encode để thực hiện mã hóa
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);



        //Nếu thuộc tính role rỗng thì gán là USER
        String roleName = request.getRole() != null ? request.getRole() : "USER";
        //nếu roleName là INSTRUCTOR thì gán is_instructor là true
        if(roleName.equals("INSTRUCTOR")){
            savedUser.setIsInstructor(true);
        } else {
            savedUser.setIsInstructor(false);
        }

        Optional<Role> role = roleRepository.findByName(roleName);
        if(role.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);

        }

        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(role.get());
        userRoleRepository.save(userRole);
        return  savedUser;
    }

    public User updateUser(Integer id, UserCreationRequest request){
//        Sử dụng Optional<T> để kiểm tra dữ liệu có NULL không
        Optional<User> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isEmpty()){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = existingUserOpt.get();
        userMapper.updateUser(user,request);
        User savedUser = userRepository.save(user);



        //Nếu thuộc tính role rỗng thì gán là USER
        String roleName = request.getRole() != null ? request.getRole() : "USER";
        //nếu roleName là INSTRUCTOR thì gán is_instructor là true
        if(roleName.equals("INSTRUCTOR")){
            savedUser.setIsInstructor(true);
        } else {
            savedUser.setIsInstructor(false);
        }
        Optional<Role> role = roleRepository.findByName(roleName);
        if(role.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);

        }

        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(role.get());
        userRoleRepository.save(userRole);
        return savedUser;
    }

    public void deleteUser(Integer id){
        if(!userRepository.existsById(id)){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
