package com.payment.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private HttpStatus httpStatus;

    public CommonException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }
}
