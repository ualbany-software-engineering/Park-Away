package com.parkway.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkway.dto.User;
import com.parkway.model.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;

    private String lastName;
    @JsonIgnore
    private String password;

    private UserStatus status;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userId, String email, String password, UserStatus status, String lastName,
                           Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = email;
        this.password = password;
        this.status = status;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {

        List<GrantedAuthority> authorities = Collections.emptyList();
        if (user.getEmail().equals("admin@parkaway.com")) {
            authorities.add(new SimpleGrantedAuthority("Admin"));
        }

        return new UserDetailsImpl(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                user.getLastName(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserStatus getStatus() {
        return status;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Long getUserId() {
        return userId;
    }


    public String getLastName() {
        return lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (this.getStatus().equals(UserStatus.ACTIVE))
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }
}
