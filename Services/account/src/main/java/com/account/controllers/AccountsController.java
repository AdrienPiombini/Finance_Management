package com.account.controllers;

import com.account.services.AccountService;
import com.account.utils.httpResponses.CreateHttpResponse;
import com.account.utils.httpResponses.DeleteHttpResponse;
import com.account.utils.httpResponses.GetAllHttpResponse;
import com.account.utils.httpResponses.GetHttpResponse;
import com.account.utils.dto.AccountDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
public class AccountsController {

    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts(){
        GetAllHttpResponse result =  accountService.getAllAccounts();

        var status = result.getStatus();

        if(result.isSuccess() == false){
            return ResponseEntity.status(status).body(result.getMessage());
        }

        return ResponseEntity.status(status).body(result.getDtoList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable(name = "id") Long accountId){
        GetHttpResponse result = accountService.getAccount(accountId);
        var status = result.getStatus();

        if(result.isSuccess() == false){
            return ResponseEntity.status(status).body(result.getMessage());
        }

        return ResponseEntity.status(status).body(result.getDto());
    }


    @PostMapping
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountDto accountDto){
        CreateHttpResponse result =  accountService.createAccount(accountDto);
        return  ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable(name = "id") Long accountId ){
        DeleteHttpResponse result = accountService.deleteAccount(accountId);
        return ResponseEntity.status(result.getStatus()).body(result.getMessage());
    }
}
