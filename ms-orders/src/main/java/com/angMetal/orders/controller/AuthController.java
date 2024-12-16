package com.angMetal.orders.controller;


import com.angMetal.orders.service.AuthService;
import com.angMetal.orders.payloads.request.LoginRequest;
import com.angMetal.orders.payloads.request.SignUpRequest;
import com.angMetal.orders.payloads.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Response> register(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("Registering user: {}", signUpRequest);
        ResponseEntity<Response> response = authService.register(signUpRequest);
        log.info("Registration response: {}", response);
        return response;
    }
}