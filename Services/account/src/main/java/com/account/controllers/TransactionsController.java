package com.account.controllers;

import com.account.services.TransactionService;
import com.account.utils.dto.TransactionDto;
import com.account.utils.httpResponses.CreateHttpResponse;
import com.account.utils.httpResponses.GetAllHttpResponse;
import com.account.utils.httpResponses.GetHttpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@CrossOrigin("*")
public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        GetAllHttpResponse<?> result = transactionService.getAllTransactions();
        var status = result.getStatus();

        if (!result.isSuccess()) {
            return ResponseEntity.status(status).body(result.getMessage());
        }

        return ResponseEntity.status(result.getStatus()).body(result.getDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable(name = "id") Long accountTransactionId) {
        GetHttpResponse<?> result = transactionService.getTransaction(accountTransactionId);
        var status = result.getStatus();

        if (!result.isSuccess()) {
            return ResponseEntity.status(status).body(result.getMessage());
        }

        return ResponseEntity.status(status).body(result.getDto());
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        CreateHttpResponse<?> result = transactionService.createTransaction(transactionDto);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }
}
