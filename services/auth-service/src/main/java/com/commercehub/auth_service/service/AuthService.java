package com.commercehub.auth_service.service;

import com.commercehub.auth_service.dto.LoginRequest;
import com.commercehub.auth_service.dto.LoginResponse;
import com.commercehub.auth_service.dto.RegisterRequest;
import com.commercehub.auth_service.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
