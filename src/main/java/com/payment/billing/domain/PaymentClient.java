package com.payment.billing.domain;

import com.payment.billing.infra.toss.dto.PaymentResponse;

public interface PaymentClient {
    PaymentResponse pay(String billingKey, int amount, String customerKey, String orderId, String orderName,
                        String customerEmail);
}
