package com.commercehub.user_service.repository;

import com.commercehub.user_service.entity.User;
import com.commercehub.user_service.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByStatus(UserStatus status);

    Optional<User> findByAuthUserId(Long authUserId);

    Page<User> findByEmailContainingIgnoreCase(
            String email,
            Pageable pageable
    );
}
