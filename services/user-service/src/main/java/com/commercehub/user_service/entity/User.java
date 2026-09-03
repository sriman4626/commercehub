package com.commercehub.user_service.entity;

import com.commercehub.user_service.enums.Gender;
import com.commercehub.user_service.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_user_id", nullable = false,unique = true)
    private Long authUserId;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    //@Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    //@Column(name = "last_name", length = 100)
    private String lastName;

   // @Column(length = 20)
    private String phone;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
}
