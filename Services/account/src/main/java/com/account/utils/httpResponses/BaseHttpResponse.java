package com.finance_management.utils.httpResponses;

import org.springframework.http.HttpStatus;

public abstract class BaseHttpResponse {
     private boolean success;
     private String message;
     private HttpStatus status;

     protected BaseHttpResponse(boolean success, String message, HttpStatus httpStatus) {
         this.success = success;
         this.message = message;
         this.status = httpStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
