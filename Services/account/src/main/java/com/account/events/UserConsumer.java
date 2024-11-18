package com.account.events;

import com.account.entities.User;
import com.account.services.UserService;
import com.finance_management.events.TopicsConfig;
import com.finance_management.events.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    private final Logger logger = LoggerFactory.getLogger(UserConsumer.class);


    private final UserService userService;

    public UserConsumer(UserService userService){
        this.userService = userService;
    }

    @KafkaListener(topics = TopicsConfig.USER_CREATED_TOPIC, groupId = "user")
    public void consume(UserCreatedEvent userCreatedEvent){
        try {
            logger.info(String.format("#### -> RECEIVED USER CREATED EVENT -> %s", userCreatedEvent));
            User user = userService.createUser(userCreatedEvent);
            logger.info(String.format("#### -> SAVED USER -> %s", user));
        } catch (Exception e) {
            logger.error("Error processing UserCreatedEvent", e);
        }

    }

}

