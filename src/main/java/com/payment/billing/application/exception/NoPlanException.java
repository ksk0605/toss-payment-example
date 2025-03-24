package com.payment.billing.application.exception;

import org.springframework.http.HttpStatus;

import com.payment.common.CommonException;

public class NoPlanException extends CommonException {
    public NoPlanException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 플랜입니다.");
    }
}
