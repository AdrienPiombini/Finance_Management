package com.finance_management.mappers;

import com.finance_management.entities.Transaction;
import com.finance_management.utils.dto.TransactionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class TransactionMapper {

    private final UserMapper userMapper;

    private final AccountMapper accountMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    TransactionMapper(AccountMapper accountMapper, UserMapper userMapper){
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
    }
    public Transaction fromTransactionDto(TransactionDto transactionDto){
        return modelMapper.map(transactionDto, Transaction.class);
    }

    public TransactionDto fromTransaction(Transaction transaction){
        var sender = accountMapper.fromAccount(transaction.getSender());
        var receiver = accountMapper.fromAccount(transaction.getReceiver());
        var userDto = userMapper.fromUser(transaction.getUser());

        var transactionDto = modelMapper.map(transaction, TransactionDto.class);

        transactionDto.setSender(sender);
        transactionDto.setReceiver(receiver);
        transactionDto.setUserDto(userDto);

        return transactionDto;
    }
}
