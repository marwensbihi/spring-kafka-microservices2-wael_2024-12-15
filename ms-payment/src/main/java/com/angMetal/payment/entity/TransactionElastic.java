package com.angMetal.payment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;  // Spring Data Elasticsearch ID
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.Date;
import enums.PaymentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "transaction_index")  // Elasticsearch index mapping
public class TransactionElastic {

    @Id  // Elasticsearch unique identifier
    private Long transactionID;

    private Double montant;

    private Date dateTransaction;

    private PaymentType typeDeTransaction;


    private Long factureVenteId;  // Stores ID of FactureVente

    private Long factureAchatId;  // Stores ID of FactureAchat
}
