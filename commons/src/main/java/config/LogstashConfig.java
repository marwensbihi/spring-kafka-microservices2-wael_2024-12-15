package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LogstashConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Configure log forwarding to Logstash or any other logging infrastructure
    public void forwardLogsToLogstash(String logMessage) {
        // Send log data to Logstash or Elasticsearch
        // For simplicity, you may integrate with ELK Stack
    }
}