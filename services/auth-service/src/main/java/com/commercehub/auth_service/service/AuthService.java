package com.commercehub.auth_service.service;

import com.commercehub.auth_service.dto.RegisterRequest;
import com.commercehub.auth_service.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);
}
