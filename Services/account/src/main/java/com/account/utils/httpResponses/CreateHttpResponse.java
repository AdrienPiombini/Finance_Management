package com.finance_management.utils.httpResponses;

import org.springframework.http.HttpStatus;

public class CreateHttpResponse<T> extends BaseHttpResponse {

    protected CreateHttpResponse(boolean success, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
    }

    public CreateHttpResponse(boolean success, String message, HttpStatus httpStatus, T valueDto) {
        super(success, message, httpStatus);
    }

    public static <T> CreateHttpResponse accountExist(T valueDto) {
        return new CreateHttpResponse(false, String.format("%s already exists", valueDto), HttpStatus.CONFLICT);
    }

    public static CreateHttpResponse serverError() {
        return new CreateHttpResponse(false, "Server Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> CreateHttpResponse success(T valueDto) {
        return new CreateHttpResponse(true, "Successfully created", HttpStatus.CREATED, valueDto);
    }

}
