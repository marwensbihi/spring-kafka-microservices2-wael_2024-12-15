package com.angMetal.orders.service;


import com.angMetal.orders.payloads.request.SignUpRequest;
import com.angMetal.orders.repositories.UserRepository;
import com.angMetal.orders.security.services.UserDetailsImpl;
import com.angMetal.orders.enums.ERole;
import com.angMetal.orders.entity.Role;
import com.angMetal.orders.entity.User;
import com.angMetal.orders.payloads.request.LoginRequest;
import com.angMetal.orders.payloads.response.JwtResponse;
import com.angMetal.orders.payloads.response.Response;
import com.angMetal.orders.repositories.RoleRepository;
import com.angMetal.orders.security.jwt.JwtUtils;
import com.angMetal.orders.utils.ResponseUtils;
import com.angMetal.orders.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserUtils userUtils;

    public ResponseEntity<Response> register(@Valid SignUpRequest signUpRequest) {
        try {
            userUtils.isUsernameValid(signUpRequest.getUsername());
            userUtils.isEmailValid(signUpRequest.getEmail());
            userUtils.isPasswordValid(signUpRequest.getPassword());
            userUtils.isPhoneNumberValid(signUpRequest.getPhoneNumber());

            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setAddress(signUpRequest.getAddress());
            user.setPhoneNumber(signUpRequest.getPhoneNumber());

            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName(ERole.ROLE_COMPUTABLE);
            roles.add(userRole);
            user.setRoles(roles);

            Long userId = userRepository.save(user).getId();

            HashMap<String, Long> data = new HashMap<>();
            data.put("userId", userId);

            return ResponseUtils.handleResponse("User registered successfully", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Response> login(LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token for the user
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Get the authenticated user's details (UserDetailsImpl is your custom implementation)
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Get user roles from authorities
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Prepare the response data
            HashMap<String, Object> data = new HashMap<>();
            data.put("token", jwt);
            data.put("user", new JwtResponse(
                    jwt,                              // JWT token
                    userDetails.getId(),              // User ID
                    userDetails.getEmail(),           // User email
                    userDetails.getUsername(),        // Username
                    userDetails.getPhoneNumber(),     // Phone number
                    userDetails.getAddress(),         // Address
                    roles                             // User roles
            ));

            // Return the response
            return ResponseUtils.handleResponse("Logged in successfully", HttpStatus.OK, data);
        } catch (Exception e) {
            // Handle any exceptions that occur during login
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

}