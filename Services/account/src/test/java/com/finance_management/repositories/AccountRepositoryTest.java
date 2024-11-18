package com.finance_management.repositories;

import com.account.entities.Account;
import com.account.entities.User;
import com.account.repositories.AccountRepository;
import com.account.repositories.UserRepository;
import com.account.utils.enums.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private Account account;

    @BeforeEach
    void setup(){
        User user = User.builder().username("Toto").build();
        userRepository.save(user);
        account = Account.builder().accountName("Livret A").balance(50).user(user).bank(Bank.CAISSE_EPARGNE).build();

        accountRepository.save(account);
    }

    @Test
    void shouldFindAccount(){
        Optional<Account> result = accountRepository.findByAccountNameAndBankAndUser(account.getAccountName(), account.getBank(), account.getUser());
        assertTrue(result.isPresent());
    }

    @Test
    void shouldNotFindAccount(){
        Optional<Account> result = accountRepository.findByAccountNameAndBankAndUser("FakeAccountName", account.getBank(), account.getUser());
        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnExistedAccount(){
        boolean result = accountRepository.existsByAccountNameAndBankAndUser(account.getAccountName(),account.getBank(), account.getUser());
        assertTrue(result);
    }

    @Test
    void shouldNotReturnANonExistentAccount(){
        boolean result = accountRepository.existsByAccountNameAndBankAndUser("FakeAccountName",account.getBank(), account.getUser());
        assertFalse(result);

    }
}