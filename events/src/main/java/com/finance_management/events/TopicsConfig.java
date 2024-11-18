package com.finance_management.events;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topics")
public class TopicsConfig {

    private String userCreatedTopic;

    public String getUserCreatedTopic() {
        return userCreatedTopic;
    }
}
