package models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import enums.PaymentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionMySQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionID;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "date_transaction", nullable = false)
    private Date dateTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_de_transaction", nullable = false)
    private PaymentType typeDeTransaction;

    @Column(name = "facture_vente_id", nullable = true)
    private Long factureVenteId; // Stores ID of FactureVente

    @Column(name = "facture_achat_id", nullable = true)
    private Long factureAchatId; // Stores ID of FactureAchat
}
