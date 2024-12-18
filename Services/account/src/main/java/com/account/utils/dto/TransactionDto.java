package com.account.utils.dto;

import com.account.utils.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {
    private double amount;
    private TransactionType transactionType;
    private AccountDto sender;
    private AccountDto receiver;
    private UserDto userDto;
    private LocalDateTime date;
}
