package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.AuthenticationRequest;
import com.udemine.course_manage.dto.response.AuthenticationResponse;
import com.udemine.course_manage.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/log-in")
    // @RequestBody để map các dữ liệu trong body vào biến request
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        boolean result_request = authenticationService.authenticate(request);
        //Trả về xác thực mật khẩu đúng hay sai trong result
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(AuthenticationResponse.builder()
                        .authenticated(result_request)
                        .build())
                .build();
    }
}
