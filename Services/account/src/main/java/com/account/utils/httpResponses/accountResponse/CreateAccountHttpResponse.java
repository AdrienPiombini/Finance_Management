package com.finance_management.utils.httpResponses.accountResponse;

import com.finance_management.utils.httpResponses.CreateHttpResponse;
import com.finance_management.utils.dto.AccountDto;
import org.springframework.http.HttpStatus;


public class CreateAccountHttpResponse extends CreateHttpResponse {
     private AccountDto accountDto;

    CreateAccountHttpResponse(boolean success, AccountDto accountDto, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
        this.accountDto = accountDto;
    }

}

