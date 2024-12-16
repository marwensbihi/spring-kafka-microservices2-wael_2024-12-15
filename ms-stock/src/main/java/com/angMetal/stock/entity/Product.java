package com.angMetal.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id") // Maps the field to the column "id" in the database
    private Long productID;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;

    @Column(name = "quantite_en_stock", nullable = false)
    private int quantiteEnStock;

    @Column(name = "taxe", nullable = false)
    private Double taxe;
}
