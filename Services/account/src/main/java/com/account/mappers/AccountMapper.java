package com.finance_management.mappers;

import com.finance_management.entities.Account;
import com.finance_management.utils.dto.AccountDto;
import org.springframework.stereotype.Service;

/**
 * We do not map transactions to avoid overloading the object unnecessarily
 * and to prevent potential serialization cycles.
 *
 */
@Service
public class AccountMapper {

    private final UserMapper userMapper;

    public AccountMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    public Account fromAccountDto(AccountDto accountDto){
        var user = userMapper.fromUserDto(accountDto.getUserDto());

        return Account.builder()
                .accountName(accountDto.getAccountName())
                .bank(accountDto.getBank())
                .balance(accountDto.getBalance())
                .currency(accountDto.getCurrency())
                .user(user)
                .build();
    }

    public AccountDto fromAccount(Account account){
        var user = userMapper.fromUser(account.getUser());

        return AccountDto.builder()
                .accountName(account.getAccountName())
                .bank(account.getBank())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .userDto(user)
                .build();
    }
}
