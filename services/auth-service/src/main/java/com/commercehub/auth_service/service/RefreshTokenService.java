package com.commercehub.auth_service.service;

import com.commercehub.auth_service.entity.RefreshToken;
import com.commercehub.auth_service.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void deleteByUser(User user);
}
