package com.commercehub.auth_service.service.impl;

import com.commercehub.auth_service.constant.Roles;
import com.commercehub.auth_service.dto.RegisterRequest;
import com.commercehub.auth_service.dto.RegisterResponse;
import com.commercehub.auth_service.entity.User;
import com.commercehub.auth_service.exception.EmailAlreadyExistsException;
import com.commercehub.auth_service.exception.UsernameAlreadyExistsException;
import com.commercehub.auth_service.repository.UserRepository;
import com.commercehub.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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


        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .message("Registration Successful")
                .build();
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
