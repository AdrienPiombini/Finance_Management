package com.finance_management.events;

import com.finance_management.entities.User;
import com.finance_management.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    private final Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "users", groupId = "user")
    public void consume(UserCreatedEvent userCreatedEvent){
        logger.info(String.format("#### -> RECEIVED USER CREATED EVENT -> %s", userCreatedEvent));

        var user = User.builder().externalId(userCreatedEvent.getId()).username(userCreatedEvent.getUsername()).build();

        var result = userRepository.save(user);

        logger.info(String.format("saved user %s", result));

    }

}
