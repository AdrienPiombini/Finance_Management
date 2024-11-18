package com.finance_management.services;

import com.finance_management.entities.Account;
import com.finance_management.entities.User;
import com.finance_management.mappers.AccountMapper;
import com.finance_management.repositories.AccountRepository;
import com.finance_management.utils.dto.AccountDto;
import com.finance_management.utils.dto.UserDto;
import com.finance_management.utils.enums.Bank;
import com.finance_management.utils.httpResponses.CreateHttpResponse;
import com.finance_management.utils.httpResponses.DeleteHttpResponse;
import com.finance_management.utils.httpResponses.GetAllHttpResponse;
import com.finance_management.utils.httpResponses.GetHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    User user = User.builder().username("Toto").build();
    Account account = Account.builder().id(1L).accountName("LedgerAccount").user(user).bank(Bank.LEDGER).build();
    AccountDto accountDto;

    @BeforeEach
    void setup(){
        UserDto userDto = UserDto.builder().username("Toto").build();
        accountDto = AccountDto.builder().accountName("LedgerAccount").userDto(userDto).bank(Bank.LEDGER).build();
    }

    @Test
    void shouldReturnAccountExist(){
        when(accountMapper.fromAccountDto(accountDto)).thenReturn(account);
        when(accountRepository.existsByAccountNameAndBankAndUser(account.getAccountName(), account.getBank(), account.getUser())).thenReturn(true);

        var result = accountService.createAccount(accountDto);

        assertFalse(result.isSuccess());
        assertEquals(CreateHttpResponse.accountExist(accountDto).getMessage(), result.getMessage());

    }

    @Test
    void shouldReturnErrorServer(){
        when(accountMapper.fromAccountDto(accountDto)).thenReturn(account);

        var result = accountService.createAccount(accountDto);

        assertFalse(result.isSuccess());
        assertEquals(CreateHttpResponse.serverError().getMessage(), result.getMessage());
    }


    @Test
    void shouldCreateAccount(){
        when(accountMapper.fromAccountDto(accountDto)).thenReturn(account);
        when(accountRepository.existsByAccountNameAndBankAndUser(account.getAccountName(), account.getBank(), account.getUser())).thenReturn(false);
        when(accountRepository.save(account)).thenReturn(account);

        var result = accountService.createAccount(accountDto);

        assertTrue(result.isSuccess());
        assertEquals(CreateHttpResponse.success(accountDto).getMessage(), result.getMessage());
    }

    @Test
    void shouldGetAllAccounts(){
        when(accountRepository.findAll()).thenReturn(List.of(account));
        when(accountMapper.fromAccount(account)).thenReturn(accountDto);

        var result = accountService.getAllAccounts();

        assertTrue(result.isSuccess());
        assertEquals(accountDto, result.getDtoList().getFirst());
    }

    @Test
    void shouldFailedGetAllAccounts(){

        // use to balance default behavior of Mockito
        when(accountRepository.findAll()).thenReturn(null);

        var result = accountService.getAllAccounts();

        assertFalse(result.isSuccess());
        assertEquals(GetAllHttpResponse.serverError().getMessage(), result.getMessage());
    }

    @Test
    void shouldGetAccount(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountMapper.fromAccount(account)).thenReturn(accountDto);

        var result = accountService.getAccount(account.getId());

        assertTrue(result.isSuccess());
        assertEquals(result.getDto(), accountDto);
    }

    @Test
    void shouldNotGetAccount(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());
        var result = accountService.getAccount(account.getId());

        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), GetHttpResponse.notFound(account.getId()).getMessage());
    }

    @Test
    void shouldFailedDeleted(){
        when(accountRepository.existsById(account.getId())).thenReturn(false);

        var result = accountService.deleteAccount(account.getId());

        assertFalse(result.isSuccess());
        assertEquals(DeleteHttpResponse.doesNotExist().getMessage(), result.getMessage());
    }

    @Test
    void shouldDeleted(){
        when(accountRepository.existsById(account.getId())).thenReturn(true);

        var result = accountService.deleteAccount(account.getId());

        assertTrue(result.isSuccess());
        assertEquals(DeleteHttpResponse.ok().getMessage(), result.getMessage());
    }

    @Test
    void shouldReturnBool(){
        when(accountRepository.findByAccountNameAndBankAndUser(account.getAccountName(), account.getBank(), account.getUser())).thenReturn(Optional.of(account));

        var result = accountService.isAccountExist(account);

        assertTrue(result);
    }
}