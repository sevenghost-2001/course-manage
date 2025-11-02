package com.udemine.course_manage.controller;

import com.udemine.course_manage.dto.request.ForgotPasswordRequest;
import com.udemine.course_manage.dto.request.MailBody;
import com.udemine.course_manage.dto.request.ResetPasswordRequest;
import com.udemine.course_manage.dto.response.ApiMessageResponse;
import com.udemine.course_manage.entity.ForgotPassword;
import com.udemine.course_manage.entity.PasswordHistory;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.ForgotPasswordRepository;
import com.udemine.course_manage.repository.PasswordHistoryRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.MailService;
import com.udemine.course_manage.service.Services.PasswordResetService;
import com.udemine.course_manage.utils.ChangePassword;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/forgot-password")
@AllArgsConstructor
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordHistoryRepository passwordHistoryRepository;


    // send email with OTP
    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        forgotPasswordRepository.deleteByUserId(user.getId()); // drop older ones
        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("Password Reset OTP: " + otp)
                .subject("Password reset request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // 5 minutes from now
                .user(user)
                .build();

        mailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("OTP sent to user's email.");
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        // Check 1: OTP valid?
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        // Check 2: OPT expired?
        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>(ErrorCode.OPT_EXPIRED.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

        // after validating OTP, checking expiration, and updating the password:
        forgotPasswordRepository.deleteByUserId(user.getId());
        // or delete the specific row if you have it loaded:
        // forgotPasswordRepository.delete(fp);

        return ResponseEntity.ok("OTP verified. You can now reset your password.");
    }

//    @PostMapping("/change-password/{email}")
//    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword,
//                                                 @PathVariable String email) {
//        if (!Objects.equals(changePassword.password(), changePassword.confirmPassword())) {
//            return new ResponseEntity<>("Passwords do not match. Please re-enter the password.", HttpStatus.EXPECTATION_FAILED);
//        }
//        MailBody mailBody = MailBody.builder()
//                .to(email)
//                .body("Your password has been successfully changed. If you did not perform this action, please contact support immediately. \n\nBest regards,\nSkillGro Team")
//                .subject("Password Changed Successfully")
//                .build();
//
//        String encodedPassword = passwordEncoder.encode(changePassword.password());
//        userRepository.updatePassword(email, encodedPassword);
//        mailService.sendSimpleMessage(mailBody);
//        return ResponseEntity.ok("Password updated successfully");
//    }

    @PostMapping("/change-password/{email}")
    @Transactional
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword req,
                                                 @PathVariable String email) {
        if (!Objects.equals(req.password(), req.confirmPassword())) {
            return new ResponseEntity<>("Passwords do not match. Please re-enter the password.",
                    HttpStatus.EXPECTATION_FAILED);
        }

        // A) get userId and current hash without touching the User entity
        Integer userId = forgotPasswordRepository.findUserIdByEmail(email);
        if (userId == null) {
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }
        String currentHash = forgotPasswordRepository.findCurrentHash(userId);

        String newRaw = req.password();

        // B) collect CURRENT + last 5 from history
        List<String> recent = new ArrayList<>();
        if (currentHash != null) recent.add(currentHash);
        recent.addAll(passwordHistoryRepository.findHashes(userId, PageRequest.of(0, 5)));

        // C) compare using encoder
        for (String oldHash : recent) {
            if (passwordEncoder.matches(newRaw, oldHash)) {
                return new ResponseEntity<>("Cannot reuse your last 5 passwords.",
                        HttpStatus.EXPECTATION_FAILED);
            }
        }

        // D) update, insert history, trim
        String newHash = passwordEncoder.encode(newRaw);
        forgotPasswordRepository.updatePassword(email, newHash);

        passwordHistoryRepository.save(
                PasswordHistory.builder()
                        .userId(userId)
                        .passwordHash(newHash)
                        .createdAt(new Date())
                        .build()
        );
        passwordHistoryRepository.trimToFive(userId);

        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("Your password has been successfully changed. If you did not perform this action, please contact support immediately. \n\nBest regards,\nSkillGro Team")
                .subject("Password Changed Successfully")
                .build();
        mailService.sendSimpleMessage(mailBod
        // E) (keep your existing mail notify)
        return ResponseEntity.ok("Password updated successfully");
    }




    private Integer otpGenerator() {
        return (int) ((Math.random() * 900_000) + 100_000); // 6 digit otp ranging from 100000 to 999999
    }



}
