package com.angMetal.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyID;

    @Column(name = "name")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters.")
    private String name;

    @Column(name = "description")
    @Size(min = 12, max = 500, message = "The description must be between 12 and 500 characters.")
    private String description;

    @Size(min = 2, max = 50, message = "The location must be between 2 and 50 characters.")
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<User> users;
    @OneToOne
    @JoinColumn(name = "banque_id")
    @JsonIgnore
    private Banque banque;

    public Company(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.users = new ArrayList<>();
        this.banque = null;
    }
}
