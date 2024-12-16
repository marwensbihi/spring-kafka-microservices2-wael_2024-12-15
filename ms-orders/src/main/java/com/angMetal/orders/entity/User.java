
package com.angMetal.orders.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID for User
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should not contain any special characters")
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Invalid phone number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)  // LAZY load for better performance
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",  // Junction table between User and Role
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}