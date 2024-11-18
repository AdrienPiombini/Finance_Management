package com.account.utils.httpResponses.accountResponse;

import com.account.utils.httpResponses.CreateHttpResponse;
import com.account.utils.dto.AccountDto;
import org.springframework.http.HttpStatus;


public class CreateAccountHttpResponse extends CreateHttpResponse {
     private AccountDto accountDto;

    CreateAccountHttpResponse(boolean success, AccountDto accountDto, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
        this.accountDto = accountDto;
    }

}

