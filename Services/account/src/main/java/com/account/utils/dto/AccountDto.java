package com.finance_management.utils.dto;

import com.finance_management.utils.enums.Bank;
import com.finance_management.utils.enums.Currency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {
    @NotBlank(message = "Need Name")
    private String accountName;
    @NotNull(message = "Need bank")
    private Bank bank;
    private double balance;
    @NotNull(message = "Need currency")
    private Currency currency;
    @Valid
    @NotNull(message = "Need user information")
    private UserDto userDto;
    @Builder.Default
    private List<TransactionDto> sentTransactions = new ArrayList<>();
    @Builder.Default
    private List<TransactionDto> receivedTransactions = new ArrayList<>();
}
