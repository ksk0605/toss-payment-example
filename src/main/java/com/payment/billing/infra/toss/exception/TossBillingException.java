package com.payment.billing.infra.toss.exception;

import org.springframework.http.HttpStatus;

import com.payment.common.CommonException;

public class TossBillingException extends CommonException {

    public TossBillingException(TossBillingErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode.getKoreanMessage());
    }

    public TossBillingException(TossBillingErrorCode errorCode, String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
