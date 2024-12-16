package com.angMetal.stock.kafka;

import com.angMetal.stock.service.StockService;
import models.FactureEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class StockConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StockConsumer.class);

    private final StockService stockService;

    @Autowired
    public StockConsumer(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Kafka Listener for stock update events from ms-order
     * @param factureEvent FactureEvent containing the stock update information
     */
    @KafkaListener(topics = "stock-events", groupId = "ms-stock-group")
    public void listenToFactureEvent(@Payload FactureEvent factureEvent) {
        try {
            logger.info("Received message to stock: {}", factureEvent.toString());
            // Delegate the processing to the StockService
           stockService.processStockUpdate(factureEvent);
        } catch (Exception e) {
            logger.error("Error processing facture event: {}", e.getMessage(), e);
        }
    }
}
