package com.edupay.serviceImpl;

import com.edupay.dto.request.LoginRequest;
import com.edupay.dto.request.RefreshTokenRequest;
import com.edupay.dto.request.RegisterRequest;
import com.edupay.dto.response.AuthResponse;
import com.edupay.dto.response.UserResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.User;
import com.edupay.mapper.UserMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.UserRepository;
import com.edupay.security.JwtTokenProvider;
import com.edupay.security.LoginAttemptService;
import com.edupay.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginAttemptService loginAttemptService;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           InstitutionRepository institutionRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           LoginAttemptService loginAttemptService,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.institutionRepository = institutionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginAttemptService = loginAttemptService;
        this.userMapper = userMapper;
    }

@Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail().toLowerCase().trim();

        // 1. Check if email is already taken
        if (userRepository.findByEmail(email).isPresent()) {
            log.warn("Registration attempt with existing email: {}", email);
            throw new BadCredentialsException("An account with this email already exists");
        }

        // 2. Create institution
        Institution institution = new Institution();
        institution.setName(request.getInstitutionName());
        institution.setType("SCHOOL");
        institution = institutionRepository.save(institution);

        // 3. Create admin user with BCrypt-encoded password
        User user = new User();
        user.setInstitution(institution);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName("Admin");
        user.setLastName(request.getInstitutionName());
        user.setIsActive(true);
        user = userRepository.save(user);

        log.info("New registration: {} (institution: {})", email, institution.getName());

        // 4. Return auth response with auto-login
        String tenantId = institution.getId().toString();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", "ADMIN");
        claims.put("user_id", user.getId().toString());
        claims.put("institution_id", tenantId);

        String accessToken = jwtTokenProvider.generateAccessToken(email, tenantId, claims);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email, tenantId, claims);

        UserResponse userResponse = userMapper.toResponse(user);

        return new AuthResponse(accessToken, refreshToken, 900L, userResponse);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String email = request.getEmail().toLowerCase().trim();

        // 1. Rate limiting check
        if (loginAttemptService.isLocked(email)) {
            log.warn("Login attempt on locked account: {}", email);
            throw new LockedException("Account temporarily locked due to too many failed attempts");
        }

        // 2. Find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    loginAttemptService.registerFailedAttempt(email);
                    log.warn("Failed login attempt (unknown email): {}", email);
                    return new BadCredentialsException("Invalid email or password");
                });

        // 3. Verify password with BCrypt cost 12
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            loginAttemptService.registerFailedAttempt(email);
            log.warn("Failed login attempt (wrong password): {}", email);
            throw new BadCredentialsException("Invalid email or password");
        }

        // 4. Check if account is active
        if (user.getIsActive() == null || !user.getIsActive()) {
            log.warn("Login attempt on inactive account: {}", email);
            throw new BadCredentialsException("Account is disabled. Contact your administrator.");
        }

        // 5. Success — reset attempts counter
        loginAttemptService.reset(email);

        // 6. Update last login timestamp
        user.setLastLoginAt(OffsetDateTime.now());
        userRepository.save(user);

        // 7. Generate tokens
        String tenantId = user.getTenantId().toString();
        String subject = user.getEmail();

        Map<String, Object> claims = new HashMap<>();
        String roleCode = user.getRole() != null ? user.getRole().getCode() : "STUDENT";
        claims.put("roles", roleCode);
        claims.put("user_id", user.getId().toString());
        claims.put("institution_id", tenantId);

        String accessToken = jwtTokenProvider.generateAccessToken(subject, tenantId, claims);
        String refreshToken = jwtTokenProvider.generateRefreshToken(subject, tenantId, claims);

        UserResponse userResponse = userMapper.toResponse(user);

        log.info("Successful login: {} (tenant: {})", email, tenantId);
        return new AuthResponse(accessToken, refreshToken, 900L, userResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse refresh(RefreshTokenRequest request, HttpServletRequest httpRequest) {
        String rawToken = request.getRefreshToken();
        if (rawToken == null || rawToken.isBlank()) {
            throw new BadCredentialsException("Refresh token is required");
        }

        // Validate refresh token
        if (!jwtTokenProvider.isTokenValid(rawToken)) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        Claims claims = jwtTokenProvider.parseAllClaims(rawToken);
        String typ = claims.get("typ", String.class);
        if (!"refresh".equals(typ)) {
            throw new BadCredentialsException("Invalid token type: expected refresh token");
        }

        String subject = claims.getSubject();
        String tenantId = jwtTokenProvider.extractTenantId(rawToken);

        if (subject == null || tenantId == null) {
            throw new BadCredentialsException("Invalid refresh token payload");
        }

        // Verify user still exists and is active
        User user = userRepository.findByEmail(subject)
                .orElseThrow(() -> new BadCredentialsException("User no longer exists"));

        if (user.getIsActive() == null || !user.getIsActive()) {
            throw new BadCredentialsException("Account is disabled");
        }

        // Generate new access token
        Map<String, Object> newClaims = new HashMap<>();
        String roleCode = user.getRole() != null ? user.getRole().getCode() : "STUDENT";
        newClaims.put("roles", roleCode);
        newClaims.put("user_id", user.getId().toString());
        newClaims.put("institution_id", tenantId);

        String newAccessToken = jwtTokenProvider.generateAccessToken(subject, tenantId, newClaims);

        UserResponse userResponse = userMapper.toResponse(user);

        return new AuthResponse(newAccessToken, rawToken, 900L, userResponse);
    }

    @Override
    @Transactional
    public void logout(String refreshToken, HttpServletRequest httpRequest) {
       
        if (refreshToken != null && !refreshToken.isBlank()) {
            try {
                String email = jwtTokenProvider.extractSubject(refreshToken);
                log.info("User logged out: {}", email);
            } catch (Exception e) {
                log.debug("Logout with invalid or expired token");
            }
        }
    }
}
