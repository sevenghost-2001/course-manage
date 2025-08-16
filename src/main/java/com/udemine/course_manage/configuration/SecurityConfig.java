package com.udemine.course_manage.configuration;

import com.udemine.course_manage.utils.CustomAuthenticationEntryPoint;
import com.udemine.course_manage.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;
    @Autowired
    //custom lại kiểu trả lỗi hết hạn token theo chuẩn ApiResponse
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final String[] PUBLIC_ENDPOINTS = {
            "/api/users",
            "/auth/token",
            "/auth/introspect",
            "/api/career-paths"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                //Yêu cầu có quyền Admin để có thể truy cập lấy danh sách người dùng, thông tin cá nhân
                request.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/users").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/courses/*").permitAll()
                        .requestMatchers("/auth/*").permitAll()
                        .anyRequest().authenticated());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //oath2ResourceServer dùng để xác thực token
        httpSecurity.oauth2ResourceServer(oauth2 ->
                // jwt() dùng để xác thực token theo chuẩn JWT
                oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter))
                        .authenticationEntryPoint(customAuthenticationEntryPoint) //Kiểm tra xem đã hết hạn token hay chưa, nếu hết hạn thì sẽ trả về lỗi 401 Unauthorized
        );
        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}