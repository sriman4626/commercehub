package com.commercehub.auth_service.client;


import com.commercehub.auth_service.dto.request.CreateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/v1/users")
    void createUser(@RequestBody CreateUserRequest request);
}
