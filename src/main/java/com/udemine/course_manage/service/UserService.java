package com.udemine.course_manage.service;

import com.udemine.course_manage.dto.request.UserCreationRequest;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.UserMapper;
import com.udemine.course_manage.repository.UserRepository;
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
    private UserMapper userMapper;

    public User createUser(UserCreationRequest request){
//        User user = new User();

        if(userRepository.existsByname(request.getName())){
//            throw new RuntimeException(ErrorCode.USER_EXISTED);
            throw new  AppException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByemail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
//        UserCreationRequest request1 = new UserCreationRequest();
//        UserCreationRequest request2 = new UserCreationRequest("A","a@gmail.com","123456","student",true);
//        UserCreationRequest request3 = new UserCreationRequest().builder()
//                .name("A")
//                .password("123456")
//                .role("instructor")
//                .build();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//        user.setRole(Role.valueOf(request.getRole().toLowerCase()));

//        user.setInstructor(request.getIsInstructor() != null ? request.getIsInstructor() : false);
//        user.onCreate();
        User user = userMapper.toUser(request);
        // Strength/ thuật toán càng phức tạp thì sẽ càng làm chậm hệ thống , mặc định độ phức tạp sẽ là 10
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        //Gọi hàm encode để thực hiện mã hóa
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
