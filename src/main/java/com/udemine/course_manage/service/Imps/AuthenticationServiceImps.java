package com.udemine.course_manage.service.Imps;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.udemine.course_manage.dto.request.AuthenticationRequest;
import com.udemine.course_manage.dto.request.IntroSpectRequest;
import com.udemine.course_manage.dto.response.AuthenticationResponse;
import com.udemine.course_manage.dto.response.IntrospectResponse;
import com.udemine.course_manage.entity.User;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.repository.UserRepository;
import com.udemine.course_manage.service.Services.AuthenticationService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImps implements AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerkey}")
    protected String SIGNER_KEY;
    PasswordEncoder passwordEncoder;

    // Số lần thử đăng nhập không thành công trước khi khóa tài khoản
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int MAX_LOCKOUTS = 3;          // consecutive temp locks → permanent
    public static final long TEMP_LOCK_MINUTES = 1;    // cooldown

    @Override
    public IntrospectResponse introspect(IntroSpectRequest request) {
        var token = request.getToken();

        JWSVerifier verifier = null;
        try {
            verifier = new MACVerifier(SIGNER_KEY.getBytes());
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        boolean verified = false;
        try {
            verified = signedJWT.verify(verifier);
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        Date expirationTime = null;
        try {
            expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        return IntrospectResponse.builder()
                .valid(verified && expirationTime != null && expirationTime.after(new Date()))
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        //Không có findByName nên ta phải khai báo thêm findByName
       User user = userRepository.findByName(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Hard stop if user hit the permanent threshold
        if (user.getLockoutCount() >= MAX_LOCKOUTS) {
            throw new AppException(ErrorCode.ACCOUNT_PERMANENTLY_LOCKED);
        }

        // Check if account is locked
        if (!user.isAccountNonLocked()) {
            // If already permanently locked, never auto-unlock
            if (user.getLockoutCount() >= MAX_LOCKOUTS) {
                throw new AppException(ErrorCode.ACCOUNT_PERMANENTLY_LOCKED);
            }
            // Temporary lock window
            if (user.getLockTime() != null &&
                    user.getLockTime().plusMinutes(TEMP_LOCK_MINUTES).isBefore(LocalDateTime.now())) {
                temporaryUnlock(user);
            } else {
                throw new AppException(ErrorCode.ACCOUNT_LOCKED);
            }
        }

        // Check password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            handleFailedAttempt(user);
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        // Successful login → reset counter
        resetFailedAttempts(user);

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .id(user.getId())
                .name(user.getName())
                .role(buildScope(user))
                .build();
    }

    @Override
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                Claim subject là đại diện cho người dùng
                .subject(user.getName())
                // issuer là người phát hành token, thường là tên ứng dụng hoặc dịch vụ
                .issuer("com.udemine.course_manage")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(10, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("userId", user.getId())
                .claim("scope", buildScope(user)) // Thêm claim scope để xác định quyền của người dùng
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize(); // Chuyển đổi JWSObject thành chuỗi
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getUserRoles())){
            user.getUserRoles().forEach(role ->{
                stringJoiner.add(role.getRole().getName());
            });
        }
        return stringJoiner.toString();
    }

    @Override
    public void handleFailedAttempt(User user) {
        int newAttempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(newAttempts);

        if (newAttempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountNonLocked(false);
            user.setLockTime(LocalDateTime.now());

            // Count this as one consecutive lockout event
            user.setLockoutCount(user.getLockoutCount() + 1);
        }

        userRepository.save(user);
    }

    @Override
    public void temporaryUnlock(User user) {
        user.setFailedAttempts(0);
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        // DO NOT reset lockoutCount here!!! we want consecutive lockouts to accumulate
        userRepository.save(user);
    }

    @Override
    public void resetFailedAttempts(User user) { // use only after a successful login
        user.setFailedAttempts(0);
        user.setLockoutCount(0); // ok to clear on success
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        userRepository.save(user);
    }
}
