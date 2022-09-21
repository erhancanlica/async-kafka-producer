package com.aaroen.kafka.producer.scheduler;

import com.aaroen.kafka.producer.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class MessageScheduler {

    private final MessageProducer messageProducerService;

    @Scheduled(fixedDelay = 20000)
    public void asyncSendMessage() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                log.info("current thread: {}", Thread.currentThread().getName());
                String message = String.format("message: %d", finalI);
                messageProducerService.sendMessageToKafka(message);
            });
        }
    }
}