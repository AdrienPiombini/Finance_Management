package com.finance_management.services;

import com.finance_management.entities.Transaction;
import com.finance_management.mappers.TransactionMapper;
import com.finance_management.repositories.TransactionRepository;
import com.finance_management.utils.dto.TransactionDto;
import com.finance_management.utils.httpResponses.CreateHttpResponse;
import com.finance_management.utils.httpResponses.GetAllHttpResponse;
import com.finance_management.utils.httpResponses.GetHttpResponse;
import com.finance_management.utils.httpResponses.transactionResponse.CreateTransactionHttpResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountService = accountService;
    }

    public GetAllHttpResponse<?> getAllTransactions() {
        var result = transactionRepository.findAll();

        if (result == null) {
            return GetAllHttpResponse.serverError();
        }

        var accountTransactionDtoList = result.stream().map(transactionMapper::fromTransaction).toList();

        return GetAllHttpResponse.dtoList(accountTransactionDtoList);
    }

    public CreateHttpResponse<?> createTransaction(TransactionDto transactionDto) {

        if(transactionDto.getAmount() <= 0){
            return CreateTransactionHttpResponse.wrongAmount();
        }

        var transaction = transactionMapper.fromTransactionDto(transactionDto);
        transaction.setDate(LocalDateTime.now());

        var ensureUser = isSenderHoldTransaction(transaction);
        if(!ensureUser){
            return CreateTransactionHttpResponse.userCannotUseThisAccount();
        }

        var result = canCreate(transaction);
        if(!result){
            return CreateTransactionHttpResponse.accountDoesNotExist();
        }

        var savedAccountTransaction = transactionRepository.save(transaction);

        if (savedAccountTransaction == null) {
            return CreateHttpResponse.serverError();
        }

        return CreateHttpResponse.success(transactionDto);
    }

    private boolean isSenderHoldTransaction(Transaction transaction) {
        var username = transaction.getUser().getUsername();
        var usernameSender = transaction.getSender().getUser().getUsername();
        return username == usernameSender;
    }

    private boolean canCreate(Transaction transaction) {

        var sender =  accountService.isAccountExist(transaction.getSender());
        var receiver = accountService.isAccountExist(transaction.getReceiver());

        return sender && receiver;
    }

    public GetHttpResponse<?> getTransaction(Long transactionId) {
        var result = transactionRepository.findById(transactionId);

        if (result.isEmpty()) {
            return GetHttpResponse.notFound(transactionId);
        }

        var accountTransactionDto = transactionMapper.fromTransaction(result.get());

        return GetHttpResponse.found(accountTransactionDto);

    }


}
