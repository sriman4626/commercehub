package com.commercehub.user_service.service;

import com.commercehub.user_service.dto.request.CreateUserRequest;
import com.commercehub.user_service.dto.request.UpdateUserRequest;
import com.commercehub.user_service.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    UserResponse getUser(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    Page<UserResponse> search(String email,Pageable pageable);
}
