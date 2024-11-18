package finance_management.security.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);
    //TODO Add in config files for sharing constant
    private static final String TOPIC = "users";

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserCreatedEvent userCreatedEvent) {
        logger.info(String.format("#### -> SENT USER CREATED EVENT for username -> %s", userCreatedEvent.getUsername()));
        this.kafkaTemplate.send(TOPIC, userCreatedEvent);
    }
}
