package com.angMetal.orders.payloads.request;

import lombok.Getter;

import java.util.Set;


@Getter
public class UpdateUserDTO {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Set<String> roles;

}
