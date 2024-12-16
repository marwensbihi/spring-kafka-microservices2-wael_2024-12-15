package com.angMetal.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banque")
public class Banque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compte_id")
    private Long compteID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "solde_initial", nullable = false)
    private Double soldeInitial;

    @Column(name = "solde_actuel", nullable = false)
    private Double soldeActuel;




}