package com.finance_management.utils.httpResponses.transactionResponse;

import com.finance_management.utils.httpResponses.CreateHttpResponse;
import org.springframework.http.HttpStatus;

public class CreateTransactionHttpResponse extends CreateHttpResponse {

    public CreateTransactionHttpResponse(boolean success, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
    }

    public static CreateTransactionHttpResponse accountDoesNotExist() {
        return new CreateTransactionHttpResponse(false, "Sender or Receiver Account does not exist", HttpStatus.BAD_REQUEST);
    }

    public static CreateTransactionHttpResponse wrongAmount(){
        return new CreateTransactionHttpResponse(false, "Amount should be positive", HttpStatus.BAD_REQUEST);
    }

    public static CreateTransactionHttpResponse userCannotUseThisAccount(){
        return new CreateTransactionHttpResponse(false, "Account does not belong to the sender", HttpStatus.UNAUTHORIZED);
    }
}
