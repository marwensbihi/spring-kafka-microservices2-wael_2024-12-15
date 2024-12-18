package com.angMetal.payment.kafka;

import models.TransactionMySQL;
import com.angMetal.payment.service.ElasticsearchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.FactureType;
import models.FactureEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    private final ElasticsearchServiceImpl elasticsearchServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;  // Jackson ObjectMapper for parsing messages

    @Autowired
    public PaymentConsumer(ElasticsearchServiceImpl elasticsearchServiceImpl) {
        this.elasticsearchServiceImpl = elasticsearchServiceImpl;
    }

    /**
     * Kafka Listener method for processing payment-related messages.
     *
     * @param factureEvent the incoming message from the Kafka topic.
     */
    @KafkaListener(topics = "payment-events", groupId = "ms-payment-group")
    public void listenPaymentEvents(FactureEvent factureEvent) {
        logger.info("Received payment event: {}", factureEvent);

        try {
            logger.info("Received message to payment: {}", factureEvent.toString());

            // Process the FactureEvent object directly
            TransactionMySQL transactionMySQL = createTransactionFromFactureEvent(factureEvent);

            // Save to MySQL and Elasticsearch
            elasticsearchServiceImpl.saveTransaction(transactionMySQL);

            // Log the transaction saved and indexed
            logger.info("Transaction saved and indexed: {}", transactionMySQL);
        } catch (Exception e) {
            logger.error("Error processing payment event: {}", factureEvent, e); // Use factureEvent here
        }
    }

    /**
     * Create a Transaction entity from the FactureEvent.
     *
     * @param factureEvent the received payment event.
     * @return a populated Transaction entity for MySQL.
     */
    private TransactionMySQL createTransactionFromFactureEvent(FactureEvent factureEvent) {

        TransactionMySQL transactionMySQL = new TransactionMySQL();
        transactionMySQL.setMontant(factureEvent.getMontantTotal());
        transactionMySQL.setDateTransaction(factureEvent.getDateEmission());
        transactionMySQL.setTypeDeTransaction(factureEvent.getPaymentType());
        if (FactureType.ACHAT.equals(factureEvent.getFactureType())) {
            transactionMySQL.setFactureAchatId(factureEvent.getFactureId());
        } else {
            transactionMySQL.setFactureVenteId(factureEvent.getFactureId());
        }
        return transactionMySQL;
    }
}
