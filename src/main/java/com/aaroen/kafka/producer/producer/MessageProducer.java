package com.aaroen.kafka.producer.producer;

import com.aaroen.kafka.producer.configuration.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaProperties kafkaProperties;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void sendMessageToKafka(String message) {
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(kafkaProperties.getTopic(), message);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("sent message to kafka: {}", message);
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.error("unable to send message= " + message, throwable);
            }
        });
        kafkaTemplate.flush();
    }
}