package com.payment.billing.application.exception;

import org.springframework.http.HttpStatus;

import com.payment.common.CommonException;

public class AlreadySubscribedException extends CommonException {
    public AlreadySubscribedException() {
        super(HttpStatus.BAD_REQUEST, "이미 구독한 사용자입니다.");
    }
}
