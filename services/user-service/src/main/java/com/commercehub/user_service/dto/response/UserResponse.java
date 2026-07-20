package com.commercehub.user_service.dto.response;

import com.commercehub.user_service.enums.Gender;
import com.commercehub.user_service.enums.UserStatus;
import com.commercehub.user_service.service.impl.UserServiceImpl;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Long id;
    private Long authUserId;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private LocalDate dateOfBirth;

    private Gender gender;

    private UserStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
