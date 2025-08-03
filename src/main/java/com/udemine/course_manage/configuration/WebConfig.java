package com.udemine.course_manage.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(new String[]{"http://localhost:63342", "http://localhost:8080", "http://localhost:63343"})
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
