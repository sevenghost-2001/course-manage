package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ForgotPasswordRequest;
import com.udemine.course_manage.dto.request.MailBody;
import com.udemine.course_manage.dto.request.ResetPasswordRequest;
import com.udemine.course_manage.dto.response.ApiMessageResponse;
import com.udemine.course_manage.entity.ForgotPassword;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.ForgotPasswordRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.MailService;
import com.udemine.course_manage.service.Services.PasswordResetService;
import com.udemine.course_manage.utils.ChangePassword;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgot-password")
@AllArgsConstructor
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    // send email with OTP
    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("Password Reset OTP: " + otp)
                .subject("Password reset request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutes from now
                .user(user)
                .build();

        mailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("OTP sent to user's email");
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP expired. Please request a new one.", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified. You can now reset your password.");
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword,
                                                 @PathVariable String email) {
        if (!Objects.equals(changePassword.password(), changePassword.confirmPassword())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.EXPECTATION_FAILED);
        }
        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password updated successfully");
    }

    private Integer otpGenerator() {
        return (int) ((Math.random() * 900_000) + 100_000); // 6 digit otp ranging from 100000 to 999999
    }



}
