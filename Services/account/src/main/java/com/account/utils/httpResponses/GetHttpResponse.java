package com.finance_management.utils.httpResponses;

import org.springframework.http.HttpStatus;

public class GetHttpResponse<T> extends BaseHttpResponse {

    private T dto;

    GetHttpResponse(boolean success, T dto, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
        this.dto = dto;
    }

    public static<T> GetHttpResponse notFound(T id){
        return new GetHttpResponse(false, null,String.format("No data found for this id : %s", id), HttpStatus.NOT_FOUND);
    }

    public static<T> GetHttpResponse found(T dto){
        return new GetHttpResponse(true, dto, "Succeed", HttpStatus.OK);
    }


    public T getDto() {
        return dto;
    }


}
