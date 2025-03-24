package com.payment.user.application.exception;

import org.springframework.http.HttpStatus;

import com.payment.common.CommonException;

public class NoUserException extends CommonException {
    public NoUserException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다.");
    }
}
