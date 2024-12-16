package com.angMetal.orders.kafka;

import models.FactureEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactureProducer {

    private final KafkaTemplate<String, FactureEvent> kafkaTemplate;
    private final KafkaTemplate<String, FactureEvent> paymentKafkaTemplate; // KafkaTemplate for PaymentEvent
    private static final String STOCK_TOPIC = "stock-events";
    private static final String PAYMENT_TOPIC = "payment-events"; // New payment topic

    /**
     * Send FactureEvent to Kafka topic based on the type of facture (VENTE or ACHAT).
     * @param factureEvent The facture event containing facture details.
     */
    public void sendFactureEvent(FactureEvent factureEvent) {
        // Send to the stock-events topic
        sendStockEvent(factureEvent);
        sendPaymentEvent(factureEvent);
    }

    /**
     * Send the FactureEvent to the stock-events Kafka topic to update the stock.
     * @param factureEvent The facture event containing stock update information.
     */
    private void sendStockEvent(FactureEvent factureEvent) {
        kafkaTemplate.send(STOCK_TOPIC, factureEvent)
                .addCallback(
                        result -> System.out.println("Stock event sent successfully to topic: " + STOCK_TOPIC),
                        ex -> System.err.println("Error sending stock event to topic: " + STOCK_TOPIC)
                );
    }

    /**
     * Send PaymentEvent to the payment-events Kafka topic.
     * @param paymentEvent The payment event containing payment details.
     */
    public void sendPaymentEvent(FactureEvent paymentEvent) {
        paymentKafkaTemplate.send(PAYMENT_TOPIC, paymentEvent)
                .addCallback(
                        result -> System.out.println("Payment event sent successfully to topic: " + PAYMENT_TOPIC),
                        ex -> System.err.println("Error sending payment event to topic: " + PAYMENT_TOPIC)
                );
    }
}
