package com.angMetal.orders.config;

import com.angMetal.orders.kafka.FactureEventSerializer;
import models.FactureEvent;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.bootstrap.servers:127.0.0.1:39092,127.0.0.1:29092}")
    private String bootstrapServers;

    @Value("${kafka.topic.payment-events}")
    private String paymentEventsTopic;

    @Value("${kafka.topic.stock-events}")
    private String stockEventsTopic;



    // Kafka Producer Configuration
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, FactureEventSerializer.class);  // Use custom serializer
        return configProps;
    }

    @Bean
    public ProducerFactory<String, FactureEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, FactureEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    // Kafka Admin Bean for managing Kafka topics
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic paymentEvents() {
        return new NewTopic(paymentEventsTopic, 3, (short) 3);
    }

    @Bean
    public NewTopic stockEvents() {
        return new NewTopic(stockEventsTopic, 3, (short) 3);
    }
}
