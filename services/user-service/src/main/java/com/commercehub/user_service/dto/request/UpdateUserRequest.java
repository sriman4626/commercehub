package com.commercehub.user_service.dto.request;

import com.commercehub.user_service.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    private String phone;

    private LocalDate dateOfBirth;

    private Gender gender;

    @Email
    @NotBlank
    private String email;
}
