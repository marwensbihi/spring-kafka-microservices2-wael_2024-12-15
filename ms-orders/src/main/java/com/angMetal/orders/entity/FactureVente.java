package com.angMetal.orders.entity;

import com.angMetal.orders.enums.StatusFacture;
import enums.PaymentType;
import lombok.*;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facture_vente")
public class FactureVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facture_id")
    private Long factureID;

    @Column(name = "dateEmission", nullable = false)
    @NotNull(message = "Emission date is required.")
    private Date dateEmission;

    @Column(name = "dateEcheance", nullable = false)
    @NotNull(message = "Echeance date is required.")
    private Date dateEcheance;

    @Column(name = "montantTotal", nullable = false)
    @NotNull(message = "Total amount is required.")
    private Double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 20)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)  // Ensure the column is not nullable
    private Client client;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "facture_vente_product", // This is the join table
            joinColumns = @JoinColumn(name = "facture_id"), // Foreign key for FactureVente
            inverseJoinColumns = @JoinColumn(name = "product_id") // Foreign key for Product
    )
    private List<Product> products; // List of associated products


}
