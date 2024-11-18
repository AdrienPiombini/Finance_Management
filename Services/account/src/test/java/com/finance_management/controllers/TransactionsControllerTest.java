package com.finance_management.controllers;

import com.account.controllers.TransactionsController;
import com.account.entities.Transaction;
import com.account.services.TransactionService;
import com.account.utils.dto.TransactionDto;
import com.account.utils.httpResponses.GetAllHttpResponse;
import com.account.utils.httpResponses.GetHttpResponse;
import com.account.utils.httpResponses.transactionResponse.CreateTransactionHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionsController.class)
class TransactionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setup(){
        transaction = Transaction.builder().id(1L).build();
    }

    @Test
    void shouldFailedGetAllTransactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(GetAllHttpResponse.serverError());

        mockMvc.perform(get("/transactions"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(GetAllHttpResponse.serverError().getMessage()));
    }

    @Test
    void shouldRetrieveAllTransactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(GetAllHttpResponse.dtoList(List.of(transaction)));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(transaction));
    }

    @Test
    void shouldNotRetrieveATransaction() throws Exception {
        var fakeTransactionId = transaction.getId();
        when(transactionService.getTransaction(fakeTransactionId)).thenReturn(GetHttpResponse.notFound(fakeTransactionId));

        mockMvc.perform(get(String.format("/transactions/%d", fakeTransactionId)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(GetHttpResponse.notFound(fakeTransactionId).getMessage()));
    }

    @Test
    void shouldRetrieveATransaction() throws Exception {
        var transactionId = transaction.getId();
        when(transactionService.getTransaction(transactionId)).thenReturn(GetHttpResponse.found(transaction));

        mockMvc.perform(get(String.format("/transactions/%d", transactionId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(GetHttpResponse.found(transaction).getDto()));
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        var request = """
                {
                "amount":300,
                "transactionType": "CREDIT"
                }
                """;
        when(transactionService.createTransaction(any(TransactionDto.class)))
                .thenReturn(CreateTransactionHttpResponse.success(request));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().string(CreateTransactionHttpResponse.success(request).getMessage()));
    }


}