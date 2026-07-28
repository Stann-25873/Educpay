package com.edupay.service;

import com.edupay.dto.request.LoginRequest;
import com.edupay.dto.request.RefreshTokenRequest;
import com.edupay.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request, HttpServletRequest httpRequest);

    AuthResponse refresh(RefreshTokenRequest request, HttpServletRequest httpRequest);

    void logout(String refreshToken, HttpServletRequest httpRequest);
}
