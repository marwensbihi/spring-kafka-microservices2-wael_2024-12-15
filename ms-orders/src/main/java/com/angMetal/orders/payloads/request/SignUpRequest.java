package com.angMetal.orders.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;


@Getter
@Setter
public class SignUpRequest {

    @NotEmpty(message = "Username cannot be NULL/empty")
    @Size(min = 3, max = 20)
    private String username;

    @NotEmpty(message = "E-mail cannot be NULL/empty")
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be NULL/empty")
    @Size(min = 6, max = 40)
    private String password;

    @NotEmpty(message = "First name cannot be NULL/empty")
    @Size(min = 1)
    private String firstName;

    @NotEmpty(message = "Last name cannot be NULL/empty")
    @Size(min = 1)
    private String lastName;

    @NotEmpty(message = "Address cannot be NULL/empty")
    @Size(min = 1)
    private String address;

    @NotEmpty(message = "Phone number cannot be NULL/empty")
    @Size(min = 1)
    private String phoneNumber;

    private Set<String> roles;
}
