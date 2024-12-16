package com.angMetal.orders.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fournisseur_id")
    private Long fournisseurID;

    @Column(nullable = false)
    private String name;

    private String adresse;

    @Column(unique = true)
    @Email(message = "Please provide a valid email.")
    private String email;

    private String numeroTel;
}
