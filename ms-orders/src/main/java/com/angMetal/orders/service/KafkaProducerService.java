package com.angMetal.orders.service;

import models.FactureEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Deprecated
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, FactureEvent> kafkaTemplate;

    private static final String FACTURE_EVENTS = "facture-events";

    /**
     * Send a FactureEvent to Kafka using KafkaTemplate.
     * @param factureEvent The facture event to be sent.
     */
    public void sendFactureEvent(FactureEvent factureEvent) {
        try {
            // Send the record asynchronously using KafkaTemplate
            kafkaTemplate.send(FACTURE_EVENTS, factureEvent.getFactureId().toString(), factureEvent)
                    .addCallback(result -> {
                        System.out.println("Message sent successfully to Kafka topic: " + FACTURE_EVENTS);
                    }, ex -> {
                        ex.printStackTrace();
                    });
        } catch (Exception e) {
            // Handle any exceptions during the Kafka send operation
            e.printStackTrace();
        }
    }

    /**
     * Alternative method for sending FactureEvent using KafkaTemplate for simplicity.
     * @param factureEvent The facture event to be sent.
     */
    public void sendFactureEventWithTemplate(FactureEvent factureEvent) {
        kafkaTemplate.send(FACTURE_EVENTS, factureEvent.getFactureId().toString(), factureEvent)
                .addCallback(result -> {
                    System.out.println("Message sent successfully to Kafka topic: " + FACTURE_EVENTS);
                }, ex -> {
                    ex.printStackTrace();
                });
    }
}
