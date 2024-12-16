package com.angMetal.orders.kafka;

import models.FactureEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class FactureEventSerializer implements Serializer<FactureEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configuration logic if needed, for example, reading configs for objectMapper
    }

    @Override
    public byte[] serialize(String topic, FactureEvent data) {
        if (data == null) {
            System.err.println("Cannot serialize null data for topic: " + topic);  // Log for debugging
            return new byte[0];  // Return an empty array instead of null (Kafka expects non-null value)
        }
        try {
            // Convert FactureEvent to JSON and then to byte array
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing FactureEvent for topic: " + topic);
            e.printStackTrace();
            throw new RuntimeException("Error serializing FactureEvent", e); // Rethrow as unchecked exception
        }
    }

    @Override
    public void close() {
    }
}
