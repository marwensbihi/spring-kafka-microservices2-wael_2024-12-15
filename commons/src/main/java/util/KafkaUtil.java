package util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaUtil {

    private KafkaProducer<String, String> producer;

    public KafkaUtil(Properties producerProps) {
        this.producer = new KafkaProducer<>(producerProps);
    }

    public void sendMessage(String topic, String message) {
        producer.send(new ProducerRecord<>(topic, message));
    }

    public void close() {
        producer.close();
    }
}