package com.parkway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkway.model.UserStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'INACTIVE','UNVERIFIED')")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @JsonIgnore
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
}
