package com.commercehub.auth_service.service;

import com.commercehub.auth_service.dto.*;
import org.springframework.security.core.Authentication;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(Authentication authentication);
}
