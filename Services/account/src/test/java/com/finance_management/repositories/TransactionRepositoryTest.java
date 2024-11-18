package com.finance_management.repositories;

import com.finance_management.entities.Transaction;
import com.finance_management.utils.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    private Transaction transaction;

    @BeforeEach
    void setup(){
        transaction = Transaction.builder()
                .transactionType(TransactionType.CREDIT)
                .date(LocalDateTime.now())
                .amount(200)
                .build();

        transactionRepository.save(transaction);
    }

    @Test
    void shouldRetrieveTransaction(){
        var result = transactionRepository.findById(transaction.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void shouldRetrieveAllTransactions(){
        var result = transactionRepository.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

}