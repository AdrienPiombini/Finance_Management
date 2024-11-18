package com.finance_management.security.repository;

import com.finance_management.security.entity.User;
import com.finance_management.security.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
         user  = User.builder().id(1L)
                .username("User")
                .password("StrongPassword")
                .role(Role.ROLE_USER)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Test
    void shouldFindUser(){
        Optional<User> result = userRepository.findByUsername(user.getUsername());
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void shouldSave(){
        long currentCount = userRepository.count();
        var savedUser = userRepository.save(User.builder().username("Test").build());
        System.out.println(savedUser);
        long newCount = userRepository.count();
        assertNotNull(savedUser.getId());
        assertEquals(newCount, currentCount + 1);

    }
}