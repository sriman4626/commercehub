package com.commercehub.auth_service.service.impl;

import com.commercehub.auth_service.client.UserServiceClient;
import com.commercehub.auth_service.constant.Roles;
import com.commercehub.auth_service.dto.*;
import com.commercehub.auth_service.dto.request.CreateUserRequest;
import com.commercehub.auth_service.entity.RefreshToken;
import com.commercehub.auth_service.entity.User;
import com.commercehub.auth_service.exception.EmailAlreadyExistsException;
import com.commercehub.auth_service.exception.UsernameAlreadyExistsException;
import com.commercehub.auth_service.repository.UserRepository;
import com.commercehub.auth_service.security.CustomUserDetails;
import com.commercehub.auth_service.security.JwtService;
import com.commercehub.auth_service.service.AuthService;
import com.commercehub.auth_service.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RefreshTokenService    refreshTokenService;

    private final UserServiceClient userServiceClient;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

       validateUser(request);

        User user=User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.ROLE_USER)
                .enabled(true)
                .build();;


        userRepository.save(user);

        CreateUserRequest createUserRequest =
                CreateUserRequest.builder()
                        .authUserId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build();

        userServiceClient.createUser(createUserRequest);

        log.info("User '{}' registered successfully",user.getUsername());

        return RegisterResponse.builder()
                .message("Registration Successful")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );


        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
        log.info("User '{}' logged in successfully.",
                userDetails.getUsername());
        String token = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUser());

        return LoginResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        //deleting old refresh token
        refreshTokenService.deleteByUser(user);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        String accessToken = jwtService.generateToken(userDetails);

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public void logout(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        refreshTokenService.deleteByUser(userDetails.getUser());
    }

    private void validateUser(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }


}
