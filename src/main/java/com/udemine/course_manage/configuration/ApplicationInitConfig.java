package com.udemine.course_manage.configuration;

import com.udemine.course_manage.entity.Role;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.entity.UserRole;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.RoleRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationInitConfig {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByName("admin").isEmpty()) {

                //1 Lấy Role Admin từ database
                Role adminRole = roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
                //2 Tạo User Admin
                User adminUser = User.builder()
                        .name("admin")
                        .email("admin@example.com")
                        .password("admin")
                        .build();
//                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
                userRepository.save(adminUser);
                // 3. Tạo userRole
                UserRole userRole = UserRole.builder()
                        .user(adminUser)
                        .role(adminRole)
                        .build();
                userRoleRepository.save(userRole);
            }
        };
    }
}
