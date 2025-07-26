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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    //PostAuthorize được sử dụng để kiểm tra quyền truy cập sau khi phương thức đã thực thi
    //PreAuthorize được sử dụng để kiểm tra quyền truy cập trước khi phương thức được thực thi
    //Nếu trùng tên với tên của người dùng đang đăng nhập thì mới cho phép truy cập
    @PostAuthorize("returnObject.name == authentication.name or hasRole('ADMIN')")
    public User getUserById(Integer id) {
        log.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public User createUser(UserCreationRequest request){
        if(userRepository.existsByname(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if(userRepository.existsByemail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(request);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Nếu role null thì mặc định là USER
        String roleName = request.getRole() != null ? request.getRole() : "USER";
        user.setIsInstructor(roleName.equalsIgnoreCase("INSTRUCTOR"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Lưu user trước để có ID
        User savedUser = userRepository.save(user);

        // Tạo userRole
        UserRole userRole = new UserRole();
        userRole.setUser(savedUser);
        userRole.setRole(role);
        userRoleRepository.save(userRole);

        // Gán vào danh sách userRoles để getRoles() hoạt động
        if (savedUser.getUserRoles() == null) {
            savedUser.setUserRoles(new ArrayList<>());
        }
        savedUser.getUserRoles().add(userRole);

        return savedUser;
    }
@PostAuthorize("ReturnObject.name == authentication.name or hasRole('ADMIN')") //Kiểm tra quyền truy cập
    public User updateUser(Integer id, UserCreationRequest request){
//        Sử dụng Optional<T> để kiểm tra dữ liệu có NULL không
        Optional<User> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isEmpty()){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = existingUserOpt.get();
        userMapper.updateUser(user,request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
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

        // Kiểm tra user đã có role này chưa
        boolean alreadyHasRole = savedUser.getUserRoles() != null &&
                savedUser.getUserRoles().stream()
                        .anyMatch(ur -> ur.getRole().getName().equalsIgnoreCase(roleName));
        if(!alreadyHasRole) {
            UserRole userRole = new UserRole();
            userRole.setUser(savedUser);
            userRole.setRole(role.get());
            user.getUserRoles().add(userRole);//Add trực tiếp
            userRoleRepository.save(userRole);
        }
        return savedUser;
    }
    @PreAuthorize("hasRole('ADMIN')") //Chỉ ADMIN mới có quyền xóa người dùng
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
