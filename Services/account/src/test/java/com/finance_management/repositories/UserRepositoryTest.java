package com.finance_management.repositories;

import com.finance_management.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldSaveUser(){
        User user = User.builder().internalId(1L).externalId(1L).username("Toto").build();
        var result = userRepository.save(user);
        assertNotNull(result);
    }

}