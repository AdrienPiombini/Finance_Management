package com.account.services;

import com.account.entities.User;
import com.account.repositories.UserRepository;
import com.finance_management.events.UserCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserCreatedEvent userCreatedEvent){
        var user = User.builder()
                .externalId(userCreatedEvent.getId())
                .username(userCreatedEvent.getUsername())
                .build();

        return userRepository.save(user);
    }
}
