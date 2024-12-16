package com.angMetal.orders.security.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.angMetal.orders.entity.User;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;  // New field
    private String address;      // New field
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    public UserDetailsImpl(Long id, String username, String email, String phoneNumber, String address,
                           String password, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;  // Initialize phoneNumber
        this.address = address;          // Initialize address
        this.password = password;
        this.roles = roles;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),  // Ensure this field is retrieved from the User entity
                user.getAddress(),      // Ensure this field is retrieved from the User entity
                user.getPassword(),
                roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;  // Getter for phoneNumber
    }

    public String getAddress() {
        return address;  // Getter for address
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }
}
