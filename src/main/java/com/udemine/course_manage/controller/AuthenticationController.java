package com.udemine.course_manage.controller;

import com.nimbusds.jose.JOSEException;
import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.AuthenticationRequest;
import com.udemine.course_manage.dto.request.IntroSpectRequest;
import com.udemine.course_manage.dto.response.AuthenticationResponse;
import com.udemine.course_manage.dto.response.IntrospectResponse;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result_request = authenticationService.authenticate(request);
        //Trả về xác thực mật khẩu đúng hay sai trong result
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(result_request)
                .build();
    }
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
}
