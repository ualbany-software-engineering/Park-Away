package com.parkway.model;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;

    private String lastName;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, Long userId, String username, String email, String lastName, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.roles = roles;
    }
}
