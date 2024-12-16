package com.angMetal.stock.config;

import models.FactureEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, FactureEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:39092,127.0.0.1:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ms-stock-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Using JsonDeserializer to convert from JSON string to FactureEvent
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        // Creating a JsonDeserializer for FactureEvent
        JsonDeserializer<FactureEvent> jsonDeserializer = new JsonDeserializer<>(FactureEvent.class);
        jsonDeserializer.addTrustedPackages("models"); // Ensure the class path is correct for FactureEvent
        jsonDeserializer.setRemoveTypeHeaders(false); // Keep type headers to ensure correct deserialization

        // Wrap JsonDeserializer with ErrorHandlingDeserializer to manage errors
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FactureEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FactureEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        // Use ContainerProperties for additional settings if needed
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setPollTimeout(3000L);

        return factory;
    }
}
