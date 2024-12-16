package com.angMetal.orders.kafka;

import models.FactureEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class FactureEventDeserializer implements Deserializer<FactureEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configuration logic if needed
    }

    @Override
    public FactureEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            System.err.println("Cannot deserialize null data for topic: " + topic);
            return null;
        }
        try {
            return objectMapper.readValue(data, FactureEvent.class);
        } catch (Exception e) {
            System.err.println("Error deserializing FactureEvent for topic: " + topic);
            e.printStackTrace();
            throw new RuntimeException("Error deserializing FactureEvent", e);
        }
    }

    @Override
    public void close() {
        // No resources to close in this case
    }
}
