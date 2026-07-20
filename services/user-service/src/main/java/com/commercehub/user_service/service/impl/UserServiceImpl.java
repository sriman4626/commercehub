package com.commercehub.user_service.service.impl;

import com.commercehub.user_service.dto.request.CreateUserRequest;
import com.commercehub.user_service.dto.request.UpdateUserRequest;
import com.commercehub.user_service.dto.response.UserResponse;
import com.commercehub.user_service.entity.User;
import com.commercehub.user_service.enums.UserStatus;
import com.commercehub.user_service.exception.DuplicateEmailException;
import com.commercehub.user_service.exception.UserNotFoundException;
import com.commercehub.user_service.mapper.UserMapper;
import com.commercehub.user_service.repository.UserRepository;
import com.commercehub.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Logger log=LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("creating user with email {}",request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id : {}", savedUser.getId());
        return userMapper.toResponse(savedUser);

    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());


        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);

        log.info("User {} marked as deleted",id);
    }

    @Override
    public Page<UserResponse> search(
            String email,
            Pageable pageable) {

        return userRepository
                .findByEmailContainingIgnoreCase(email, pageable)
                .map(userMapper::toResponse);
    }
}
