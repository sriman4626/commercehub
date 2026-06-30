package com.commercehub.auth_service.controller;

import com.commercehub.auth_service.dto.RegisterRequest;
import com.commercehub.auth_service.dto.RegisterResponse;
import com.commercehub.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        System.out.println(">>> REGISTER API HIT <<<");

        return ResponseEntity.ok(authService.register(request));
    }
}