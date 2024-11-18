package com.finance_management.controllers;

import com.finance_management.entities.Account;
import com.finance_management.services.AccountService;
import com.finance_management.utils.dto.AccountDto;
import com.finance_management.utils.httpResponses.DeleteHttpResponse;
import com.finance_management.utils.httpResponses.GetAllHttpResponse;
import com.finance_management.utils.httpResponses.GetHttpResponse;
import com.finance_management.utils.httpResponses.accountResponse.CreateAccountHttpResponse;
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

@WebMvcTest(AccountsController.class)
class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setup(){
        account = Account.builder().id(1L).build();
    }

    @Test
    void shouldFailedGetAllAccounts() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(GetAllHttpResponse.serverError());

        mockMvc.perform(get("/accounts"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(GetAllHttpResponse.serverError().getMessage()));
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(GetAllHttpResponse.dtoList(List.of(account)));
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(account));
    }

    @Test
    void shouldNotFoundAccount() throws Exception {
        var fakeAccountId = 1L;
        when(accountService.getAccount(fakeAccountId)).thenReturn(GetHttpResponse.notFound(fakeAccountId));

        mockMvc.perform(get(String.format("/accounts/%d", fakeAccountId)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(GetHttpResponse.notFound(fakeAccountId).getMessage()));
    }

    @Test
    void shouldFoundAccount() throws Exception {
        var accountId = account.getId();
        when(accountService.getAccount(accountId)).thenReturn(GetHttpResponse.found(account));

        mockMvc.perform(get(String.format("/accounts/%d", accountId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(GetHttpResponse.found(account).getDto()));
    }

    @Test
    void shouldCreateAccount() throws Exception {
        var request = """
            {
              "accountName": "Livret A",
              "bank": "LEDGER",
              "currency": "EUR",
              "userDto": {
                  "username": "toto"
              }
            }
            """;
        when(accountService.createAccount(any(AccountDto.class))).thenReturn(CreateAccountHttpResponse.success(request));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().string(CreateAccountHttpResponse.success(request).getMessage()));
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        var accountId = account.getId();
        when(accountService.deleteAccount(accountId)).thenReturn(DeleteHttpResponse.ok());

        mockMvc.perform(delete(String.format("/accounts/%d", accountId)))
                .andExpect(status().isNoContent())
                .andExpect(content().string(DeleteHttpResponse.ok().getMessage()));

    }
}