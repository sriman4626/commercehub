package com.commercehub.user_service.dto.request;

import com.commercehub.user_service.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserRequest {

    @NotNull
    private Long authUserId;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;


}
