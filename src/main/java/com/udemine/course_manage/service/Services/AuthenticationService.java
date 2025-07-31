package com.udemine.course_manage.service.Services;

import com.udemine.course_manage.dto.request.AuthenticationRequest;
import com.udemine.course_manage.dto.request.IntroSpectRequest;
import com.udemine.course_manage.dto.response.AuthenticationResponse;
import com.udemine.course_manage.dto.response.IntrospectResponse;
import com.udemine.course_manage.entity.User;

public interface AuthenticationService {
    IntrospectResponse introspect(IntroSpectRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(User user);
    String buildScope(User user);
}
