package com.commercehub.user_service.mapper;

import com.commercehub.user_service.dto.request.CreateUserRequest;
import com.commercehub.user_service.dto.response.UserResponse;
import com.commercehub.user_service.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
     User toEntity(CreateUserRequest userRequest);

     UserResponse toResponse(User user);
     //User toResponse(UserResponse userResponse);

     List<UserResponse> toResponse(List<User> users);
}
