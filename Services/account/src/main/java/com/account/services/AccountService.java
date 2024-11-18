package com.account.services;

import com.account.repositories.AccountRepository;
import com.account.entities.Account;
import com.account.mappers.AccountMapper;
import com.account.utils.httpResponses.CreateHttpResponse;
import com.account.utils.httpResponses.DeleteHttpResponse;
import com.account.utils.httpResponses.GetAllHttpResponse;
import com.account.utils.httpResponses.accountResponse.CreateAccountHttpResponse;
import com.account.utils.httpResponses.GetHttpResponse;
import com.account.utils.dto.AccountDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }


    public CreateHttpResponse createAccount(AccountDto accountDto) {

        var account = accountMapper.fromAccountDto(accountDto);

        boolean result = canCreateAccount(account);
        if (result == false) {
            return CreateAccountHttpResponse.accountExist(accountDto);
        }

        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        var savedAccount = accountRepository.save(account);

        if (savedAccount == null) {
            return CreateAccountHttpResponse.serverError();
        }

        return CreateAccountHttpResponse.success(accountDto);
    }

    private boolean canCreateAccount(Account account) {
        // cannot create account if already exist
        return !accountRepository.existsByAccountNameAndBankAndUser(account.getAccountName(), account.getBank(), account.getUser());
    }

    public GetAllHttpResponse getAllAccounts() {
        List<Account> result = accountRepository.findAll();
        if (result == null) {
            return GetAllHttpResponse.serverError();
        }

        List<AccountDto> accountDtoList = result.stream().map(account -> accountMapper.fromAccount(account)).toList();
        return GetAllHttpResponse.dtoList(accountDtoList);
    }


    public GetHttpResponse getAccount(Long accountId) {
        Optional<Account> result = accountRepository.findById(accountId);
        if (result.isEmpty()) {
            return GetHttpResponse.notFound(accountId);
        }

        AccountDto accountDto = accountMapper.fromAccount(result.get());
        return GetHttpResponse.found(accountDto);

    }

    public DeleteHttpResponse deleteAccount(Long accountId) {
        boolean result = canDelete(accountId);

        if (result == false) {
            return DeleteHttpResponse.doesNotExist();
        }

        accountRepository.deleteById(accountId);
        return DeleteHttpResponse.ok();
    }

    private boolean canDelete(Long accountId) {
        return accountRepository.existsById(accountId);
    }

    public boolean isAccountExist(Account account){
        var result = accountRepository.findByAccountNameAndBankAndUser(account.getAccountName(), account.getBank(), account.getUser());
        return result.isPresent();
    }

}
