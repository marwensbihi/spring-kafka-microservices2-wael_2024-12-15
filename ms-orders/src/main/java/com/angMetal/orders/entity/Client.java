package com.angMetal.orders.entity;


import enums.TypeClient;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientID;

    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters.")
    private String name;

    @Column
    private String adresse;

    @Column(unique = true)
    @Email(message = "Please provide a valid email.")
    private String email;

    @Column
    private String numeroTel;

    @Enumerated(EnumType.STRING)
    private TypeClient typeClient;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Devis> devis;

    public Client(Long clientID, String name, String adresse, String email, String numeroTel, TypeClient typeClient) {
        this.clientID = clientID;
        this.name = name;
        this.adresse = adresse;
        this.email = email;
        this.numeroTel = numeroTel;
        this.typeClient = typeClient;
    }
}
