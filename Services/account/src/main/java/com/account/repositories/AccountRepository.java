package com.finance_management.repositories;

import com.finance_management.entities.Account;
import com.finance_management.entities.User;
import com.finance_management.utils.enums.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNameAndBankAndUser(String accountName, Bank bank, User user);
    Optional<Account> findByAccountNameAndBankAndUser(String accountName, Bank bank, User user);
}


