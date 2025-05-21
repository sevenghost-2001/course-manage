package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();

        if(userRepository.existsByname(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole().toLowerCase()));
        user.setInstructor(request.getInstructor() != null ? request.getInstructor() : false);
        user.onCreate();
        return  userRepository.save(user);
    }

    public User updateUser(Integer id, UserCreationRequest request){
//        Sử dụng Optional<T> để kiểm tra dữ liệu có NULL không
        Optional<User> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isEmpty()){
            throw  new RuntimeException("User not found with id: " + id);
        }

        User user = existingUserOpt.get();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole().toLowerCase()));
        user.setInstructor(request.getInstructor());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        if(!userRepository.existsById(id)){
            throw  new RuntimeException("User not found with id: "+ id);
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
