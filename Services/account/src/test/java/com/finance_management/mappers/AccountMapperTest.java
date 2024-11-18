package com.finance_management.mappers;

import com.account.entities.Account;
import com.account.entities.User;
import com.account.mappers.AccountMapper;
import com.account.utils.dto.AccountDto;
import com.account.utils.dto.UserDto;
import com.account.utils.enums.Bank;
import com.account.utils.enums.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void shouldMapFromAccount(){
       User user = new User(1L, 1L, "TotoUsername");

        var account = Account.builder()
                .id(1L)
                .accountName("Livret A")
                .bank(Bank.LEDGER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .balance(200d)
                .currency(Currency.ETH)
                .user(user)
                .build();

        var accountDto = accountMapper.fromAccount(account);

        assertEquals(user.getUsername(), accountDto.getUserDto().getUsername());
        assertEquals(account.getAccountName(), accountDto.getAccountName());
        assertEquals(account.getBank(), accountDto.getBank());
        assertEquals(account.getBalance(), accountDto.getBalance());
    }

    @Test
    public void shouldMapFromAccountDto(){
        UserDto userDto = UserDto.builder()
                .username("Toto")
                .externalId(1L)
                .build();

        var accountDto = AccountDto.builder()
                .accountName("Livret A")
                .bank(Bank.LEDGER)
                .balance(200d)
                .currency(Currency.ETH)
                .userDto(userDto)
                .build();

        var account = accountMapper.fromAccountDto(accountDto);

        assertEquals(accountDto.getAccountName(), account.getAccountName());
        assertEquals(accountDto.getBank(), account.getBank());
        assertEquals(accountDto.getBalance(), account.getBalance());
        assertEquals(accountDto.getCurrency(), account.getCurrency());
        assertEquals(userDto.getUsername(), account.getUser().getUsername());
    }

}