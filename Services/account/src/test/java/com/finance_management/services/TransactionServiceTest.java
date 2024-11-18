package com.finance_management.services;

import com.finance_management.entities.Account;
import com.finance_management.entities.Transaction;
import com.finance_management.entities.User;
import com.finance_management.mappers.TransactionMapper;
import com.finance_management.repositories.TransactionRepository;
import com.finance_management.utils.dto.TransactionDto;
import com.finance_management.utils.httpResponses.GetAllHttpResponse;
import com.finance_management.utils.httpResponses.GetHttpResponse;
import com.finance_management.utils.httpResponses.transactionResponse.CreateTransactionHttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;


    @Test
    void shouldFailedRetrieveAllTransactions(){
        Mockito.when(transactionRepository.findAll()).thenReturn(null);
        var result = transactionService.getAllTransactions();
        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), GetAllHttpResponse.serverError().getMessage());
    }

    @Test
    void shouldRetrieveAllTransaction(){
        var result = transactionService.getAllTransactions();
        assertTrue(result.isSuccess());
    }

    @Test
    void failedCreatedTransaction(){
        var transactionDto = TransactionDto.builder().build();
        var result = transactionService.createTransaction(transactionDto);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), CreateTransactionHttpResponse.wrongAmount().getMessage());
    }

    @Test
    void createTransactionWithUnauthorizedUser(){
        var user = User.builder().username("toto").build();
        var senderDifferentFromUser = Account.builder().user(User.builder().username("otherName").build()).build();
        var tx = Transaction.builder().user(user).sender(senderDifferentFromUser).build();

        var transactionDto = TransactionDto.builder().amount(10).date(LocalDateTime.now()).build();
        Mockito.when(transactionMapper.fromTransactionDto(transactionDto)).thenReturn(tx);
        var result = transactionService.createTransaction(transactionDto);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), CreateTransactionHttpResponse.userCannotUseThisAccount().getMessage());
    }

    @Test
    void createTransactionOnNoExistentAccount(){
        var user = User.builder().username("toto").build();
        var txDto = TransactionDto.builder().amount(10).build();
        var account = Account.builder().user(user).build();
        var tx = Transaction.builder().user(user).sender(account).receiver(account).build();
        Mockito.when(accountService.isAccountExist(any(Account.class))).thenReturn(false);
        Mockito.when(transactionMapper.fromTransactionDto(txDto)).thenReturn(tx);

        var result = transactionService.createTransaction(txDto);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), CreateTransactionHttpResponse.accountDoesNotExist().getMessage());
    }

    @Test
    void createTransactionErrorServer(){
        var user = User.builder().username("toto").build();
        var txDto = TransactionDto.builder().amount(10).build();
        var account = Account.builder().user(user).build();
        var tx = Transaction.builder().user(user).sender(account).receiver(account).build();
        Mockito.when(accountService.isAccountExist(any(Account.class))).thenReturn(true);
        Mockito.when(transactionMapper.fromTransactionDto(txDto)).thenReturn(tx);

        var result = transactionService.createTransaction(txDto);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), CreateTransactionHttpResponse.serverError().getMessage());
    }

    @Test
    void createTransaction(){
        var user = User.builder().username("toto").build();
        var txDto = TransactionDto.builder().amount(10).build();
        var account = Account.builder().user(user).build();
        var tx = Transaction.builder().user(user).sender(account).receiver(account).build();
        Mockito.when(accountService.isAccountExist(any(Account.class))).thenReturn(true);
        Mockito.when(transactionMapper.fromTransactionDto(txDto)).thenReturn(tx);
        Mockito.when(transactionRepository.save(tx)).thenReturn(tx);

        var result = transactionService.createTransaction(txDto);

        assertTrue(result.isSuccess());
        assertEquals(result.getMessage(), CreateTransactionHttpResponse.success(txDto).getMessage());
    }

    @Test
    void shouldNotFoundTransaction(){
        var noExistentId = 1L;
        var result = transactionService.getTransaction(noExistentId);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessage(), GetHttpResponse.notFound(noExistentId).getMessage());
    }

    @Test
    void shouldFountTransaction(){
        var existentId = 1L;
        var tx = Transaction.builder().build();
        Mockito.when(transactionRepository.findById(existentId)).thenReturn(Optional.of(tx));

        var result = transactionService.getTransaction(existentId);

        assertTrue(result.isSuccess());
        assertEquals(result.getMessage(), GetHttpResponse.found(existentId).getMessage());
    }

}