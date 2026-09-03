package com.commercehub.user_service.controller;

import com.commercehub.user_service.dto.request.CreateUserRequest;
import com.commercehub.user_service.dto.request.UpdateUserRequest;
import com.commercehub.user_service.dto.response.UserResponse;
import com.commercehub.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/internal")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(service.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(
            @PathVariable Long id,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role) {

        return ResponseEntity.ok(service.getUser(id,username,role));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestHeader("X-User-Role") String role,
            Pageable pageable) {
        if (!"ROLE_ADMIN".equals(role)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only admins can view all users"
            );
        }
        return ResponseEntity.ok(service.getAllUsers(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @Valid @RequestBody UpdateUserRequest request) {


        return ResponseEntity.ok(service.updateUser(id, request, username,role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") String role) {
        if(!"ROLE_ADMIN".equals(role)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,"Only admins can delete users"
            );
        }

        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> search(
            @RequestHeader("X-User-Role") String role,
            @RequestParam String email,
            Pageable pageable) {

        if (!"ROLE_ADMIN".equals(role)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only admins can search users"
            );
        }

        return ResponseEntity.ok(
                service.search(email, pageable)
        );
    }
}
