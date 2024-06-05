package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "myTopic", groupId = "myGroup")
    public void listen(String message) {
        String traceId = MDC.get("traceId");
        logger.info("Received message with traceId {}: {}", traceId, message);
        // Processing the message
        MDC.clear();
    }
}
