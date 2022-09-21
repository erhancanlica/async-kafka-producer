package com.aaroen.kafka.producer.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("kafka-producer.kafka")
public class KafkaProperties {
    private String topic;
    private Short replicaSize;
    private Integer partitionSize;
    private String bootstrapServers;
}