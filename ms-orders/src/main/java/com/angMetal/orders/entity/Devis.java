package com.angMetal.orders.entity;



import com.angMetal.orders.entity.payloads.DevisProduct;
import com.angMetal.orders.enums.StatusDevis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devis")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devis_id")
    private Long devisId;

    @Column(name = "dateCreation", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Creation date is required.")
    private Date dateCreation;

    @Column(name = "dateExpiration", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Expiration date is required.")
    private Date dateExpiration;

    @Column(name = "montantTotal", nullable = false)
    @NotNull(message = "Total amount is required.")
    private Double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusDevis statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientID", nullable = false)
    @NotNull
    private Client client;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DevisProduct> devisProducts;
}
