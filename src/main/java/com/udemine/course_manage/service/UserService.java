package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.UserMapper;
import com.udemine.course_manage.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User createUser(UserCreationRequest request){
//        User user = new User();

        if(userRepository.existsByname(request.getName()) || userRepository.existsByemail(request.getEmail())){
            throw new AppException(ErrorCode.INFORMATION_EXISTED);
        }

//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//        user.setRole(Role.valueOf(request.getRole().toLowerCase()));
//        user.setInstructor(request.getIsInstructor() != null ? request.getIsInstructor() : false);
//        user.onCreate();
        User user = userMapper.toUser(request);
        return  userRepository.save(user);
    }

    public User updateUser(Integer id, UserCreationRequest request){
//        Sử dụng Optional<T> để kiểm tra dữ liệu có NULL không
        Optional<User> existingUserOpt = userRepository.findById(id);
        if(existingUserOpt.isEmpty()){
            throw  new RuntimeException("User not found with id: " + id);
        }

        User user = existingUserOpt.get();
        userMapper.updateUser(user,request);
        return userRepository.save(user);

//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//        user.setRole(Role.valueOf(request.getRole().toLowerCase()));
//        user.setInstructor(request.getIsInstructor());


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
