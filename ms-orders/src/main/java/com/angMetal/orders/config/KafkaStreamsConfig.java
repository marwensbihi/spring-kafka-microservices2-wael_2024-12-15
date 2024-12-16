package com.angMetal.orders.config;

import com.angMetal.orders.kafka.FactureEventSerde;
import models.FactureEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaStreamsConfig {

    private static final String STOCK_TOPIC = "stock-events";
    private static final String PAYMENT_TOPIC = "payment-events";

    @Bean
    public KStream<String, FactureEvent> kStream(StreamsBuilder streamsBuilder) {
        // Create the stream from the topic using custom Serde
        KStream<String, FactureEvent> paymentStream = streamsBuilder.stream(PAYMENT_TOPIC,
                Consumed.with(Serdes.String(), new FactureEventSerde()));

        KStream<String, FactureEvent> stockStream = streamsBuilder.stream(STOCK_TOPIC,
                Consumed.with(Serdes.String(), new FactureEventSerde()));

        // Example processing of the stream
        paymentStream.merge(stockStream)
                .foreach((key, value) -> {
                    System.out.println("Processing FactureEvent with key: " + key + ", value: " + value);
                });

        return paymentStream;  // Return the stream for further processing
    }
}
