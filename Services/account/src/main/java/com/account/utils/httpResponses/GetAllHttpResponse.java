package com.finance_management.utils.httpResponses;

import org.springframework.http.HttpStatus;

import java.util.List;

public class GetAllHttpResponse<T> extends BaseHttpResponse {

    private List<T> dtoList;

    public List<T> getDtoList() {
        return dtoList;
    }

    protected GetAllHttpResponse(boolean success, List<T> dtoList, String message, HttpStatus httpStatus) {
        super(success, message, httpStatus);
        this.dtoList = dtoList;
    }

    public static GetAllHttpResponse serverError(){
        return new GetAllHttpResponse(false, null, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static<T> GetAllHttpResponse dtoList(List<T> dtoList){
        return new GetAllHttpResponse(true, dtoList, "Succeed", HttpStatus.OK);
    }
}
