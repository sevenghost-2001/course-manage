package com.udemine.course_manage.controller;

import com.nimbusds.jose.JOSEException;
import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.AuthenticationRequest;
import com.udemine.course_manage.dto.request.IntroSpectRequest;
import com.udemine.course_manage.dto.response.AuthenticationResponse;
import com.udemine.course_manage.dto.response.IntrospectResponse;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    UserRepository userRepository;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result_request = authenticationService.authenticate(request);
        //Trả về xác thực mật khẩu đúng hay sai trong result
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(result_request)
                .build();
    }

//    @PostMapping("/token")
//    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
//        User user = userRepository.findByName(request.getName())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        if (!user.isAccountNonLocked()) {
//            if (user.getLockTime() != null &&
//                    user.getLockTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
//                authenticationService.resetFailedAttempts(user); // auto-unlock
//            } else {
//                throw new AppException(ErrorCode.ACCOUNT_LOCKED);
//            }
//        }
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
//
//        if (!authenticated) {
//            authenticationService.handleFailedAttempt(user);
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
//
//        authenticationService.resetFailedAttempts(user);
//        String token = authenticationService.generateToken(user);
//
//        return ApiResponse.<AuthenticationResponse>builder()
//                .code(1000)
//                .result(AuthenticationResponse.builder()
//                        .token(token)
//                        .authenticated(true)
//                        .build())
//                .build();
//    }



    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntroSpectRequest request){
        IntrospectResponse result_request;
        try {
            result_request = authenticationService.introspect(request);
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.ERROR_VERIFY);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.ERROR_VERIFY);
        }
        //Trả về xác thực mật khẩu đúng hay sai trong result
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .result(result_request)
                .build();
    }

//    @PostMapping("/signup")
//    public ApiResponse<AuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
//        User user = authenticationService.register(request);
//        return ApiResponse.<AuthenticationResponse>builder()
//                .code(1000)
//                .result(result)
//                .build();
//    }
}
