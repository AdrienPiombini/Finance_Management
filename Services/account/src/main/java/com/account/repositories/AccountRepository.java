package com.account.repositories;

import com.account.entities.Account;
import com.account.entities.User;
import com.account.utils.enums.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNameAndBankAndUser(String accountName, Bank bank, User user);
    Optional<Account> findByAccountNameAndBankAndUser(String accountName, Bank bank, User user);
}


