package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.MailBody;
import com.udemine.course_manage.entity.PasswordResetToken;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.repository.PasswordResetTokenRepository;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.MailService;
import com.udemine.course_manage.service.Services.PasswordResetService;
import com.udemine.course_manage.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    // Frontend URL that hosts the reset form
    private static final String RESET_URL_BASE = "https://your-frontend.com/reset-password?token=";

    @Override
    public void requestReset(String email) {
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email);

        // Always behave the same regardless of existence to prevent enumeration
        userOpt.ifPresent(user -> {
            // Invalidate other active tokens (optional but good hygiene)
            tokenRepository.deleteByUserIdAndUsedFalse(user.getId());

            String rawToken = TokenUtil.generateToken();               // random 256-bit
            String tokenHash = TokenUtil.sha256(rawToken);             // store only hash

            PasswordResetToken prt = new PasswordResetToken();
            prt.setUserId(user.getId());
            prt.setTokenHash(tokenHash);
            prt.setExpiresAt(Instant.now().plus(Duration.ofMinutes(45)));
            tokenRepository.save(prt);

            String link = RESET_URL_BASE + rawToken;
            String body = """
                    We received a request to reset your password.
                    
                    Click the link below to set a new password (valid for 45 minutes):
                    %s
                    
                    If you didn't request this, you can ignore this email.
                    """.formatted(link);

            MailBody mailBody = new MailBody(
                    user.getEmail(),
                    "Password Reset Request",
                    body
            );
            mailService.sendSimpleMessage(mailBody);
        });
    }

    @Override
    public void performReset(String rawToken, String newPassword) {
        String tokenHash = TokenUtil.sha256(rawToken);

        PasswordResetToken prt = tokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token"));

        if (prt.isUsed() || prt.getExpiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expired or used token");
        }

        User user = userRepository.findById(prt.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        prt.setUsed(true);
        tokenRepository.save(prt);
    }
}
