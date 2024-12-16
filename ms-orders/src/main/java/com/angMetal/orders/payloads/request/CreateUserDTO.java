package com.angMetal.orders.payloads.request;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;



@Getter
public class CreateUserDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Set<String> roles;
}