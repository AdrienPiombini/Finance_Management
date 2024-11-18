package com.finance_management.utils.httpResponses;

import org.springframework.http.HttpStatus;

public class DeleteHttpResponse extends BaseHttpResponse {

    protected DeleteHttpResponse(boolean success, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
    }

    public static DeleteHttpResponse doesNotExist(){
        return new DeleteHttpResponse(false, "Value does not exist", HttpStatus.NOT_FOUND);
    }

    public static DeleteHttpResponse ok(){
        return new DeleteHttpResponse(true, "Value deleted successfully", HttpStatus.NO_CONTENT);
    }
}
