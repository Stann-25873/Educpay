package com.edupay.controller;

import com.edupay.dto.request.LoginRequest;
import com.edupay.dto.request.RefreshTokenRequest;
import com.edupay.dto.response.AuthResponse;
import com.edupay.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${edupay.security.refresh-cookie-name:refresh_token}")
    private String refreshCookieName;

    @Value("${edupay.jwt.refresh-token-ttl-seconds:2592000}")
    private long refreshTokenTtlSeconds;

    @Value("${edupay.security.refresh-cookie-same-site:Strict}")
    private String refreshCookieSameSite;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletRequest httpRequest) {
        AuthResponse response = authService.login(request, httpRequest);

        ResponseCookie refreshCookie = ResponseCookie.from(refreshCookieName, response.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite(refreshCookieSameSite)
                .path("/api/auth/refresh")
                .maxAge(refreshTokenTtlSeconds)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody(required = false) RefreshTokenRequest body,
                                                 HttpServletRequest httpRequest) {
        String token = null;
        if (body != null && body.getRefreshToken() != null && !body.getRefreshToken().isBlank()) {
            token = body.getRefreshToken();
        } else if (httpRequest.getCookies() != null) {
            for (var cookie : httpRequest.getCookies()) {
                if (refreshCookieName.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(token);
        AuthResponse response = authService.refresh(request, httpRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(@RequestBody(required = false) RefreshTokenRequest body,
                                        HttpServletRequest httpRequest) {
        String token = null;
        if (body != null && body.getRefreshToken() != null && !body.getRefreshToken().isBlank()) {
            token = body.getRefreshToken();
        } else if (httpRequest.getCookies() != null) {
            for (var cookie : httpRequest.getCookies()) {
                if (refreshCookieName.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        authService.logout(token, httpRequest);

        // Clear refresh cookie
        ResponseCookie clearCookie = ResponseCookie.from(refreshCookieName, "")
                .httpOnly(true)
                .secure(true)
                .sameSite(refreshCookieSameSite)
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthResponse> me() {
        return ResponseEntity.ok().build();
    }
}
