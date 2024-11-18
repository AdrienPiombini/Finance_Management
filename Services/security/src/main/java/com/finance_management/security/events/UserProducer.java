package com.finance_management.security.events;

import com.finance_management.events.TopicsConfig;
import com.finance_management.events.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserCreatedEvent userCreatedEvent) {
        logger.info(String.format("#### -> SENT USER CREATED EVENT for username -> %s", userCreatedEvent.getUsername()));

        this.kafkaTemplate.send(TopicsConfig.USER_CREATED_TOPIC, userCreatedEvent);
    }
}
