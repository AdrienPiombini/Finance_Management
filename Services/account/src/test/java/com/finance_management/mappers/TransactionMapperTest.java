package com.finance_management.mappers;

import com.finance_management.entities.Account;
import com.finance_management.entities.Transaction;
import com.finance_management.entities.User;
import com.finance_management.utils.dto.AccountDto;
import com.finance_management.utils.dto.TransactionDto;
import com.finance_management.utils.dto.UserDto;
import com.finance_management.utils.enums.Bank;
import com.finance_management.utils.enums.Currency;
import com.finance_management.utils.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void shouldMapFromTransaction(){
        var user = new User(1L, 1L, "TotoUsername");

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


        Transaction tx = Transaction.builder()
                .amount(100)
                .transactionType(TransactionType.CREDIT)
                .date(LocalDateTime.now())
                .sender(account)
                .receiver(account)
                .user(user)
                .build();

        var txDto = transactionMapper.fromTransaction(tx);

        assertEquals(txDto.getAmount(), tx.getAmount());
        assertEquals(txDto.getTransactionType(), tx.getTransactionType());
        assertEquals(txDto.getSender().getAccountName(), tx.getSender().getAccountName());
        assertEquals(txDto.getReceiver().getBank(), tx.getReceiver().getBank());
        assertEquals(txDto.getUserDto().getUsername(), tx.getUser().getUsername());
        assertEquals(txDto.getDate(), tx.getDate());
    }

    @Test
    public void shouldMapFromTransactionDto(){
        var userDto = new UserDto(1L, "TotoUsername");

        var accountDto = AccountDto.builder()
                .accountName("Livret A")
                .bank(Bank.LEDGER)
                .balance(200d)
                .currency(Currency.ETH)
                .userDto(userDto)
                .build();


        TransactionDto txDto = TransactionDto.builder()
                .amount(100)
                .transactionType(TransactionType.CREDIT)
                .date(LocalDateTime.now())
                .receiver(accountDto)
                .sender(accountDto)
                .userDto(userDto)
                .build();

        var tx = transactionMapper.fromTransactionDto(txDto);
        System.out.println(tx);

        assertEquals(txDto.getAmount(), tx.getAmount());
        assertEquals(txDto.getTransactionType(), tx.getTransactionType());
        assertEquals(txDto.getSender().getAccountName(), tx.getSender().getAccountName());
        assertEquals(txDto.getReceiver().getBank(), tx.getReceiver().getBank());
        assertEquals(txDto.getUserDto().getUsername(), tx.getUser().getUsername());
        assertEquals(txDto.getDate(), tx.getDate());
    }

}