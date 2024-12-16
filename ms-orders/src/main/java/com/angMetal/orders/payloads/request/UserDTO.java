package com.angMetal.orders.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should not contain any special characters")
    private Long id;
    private String username;

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private Set<String> roles; // Include roles as a Set of Strings
}
