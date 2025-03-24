package com.payment.billing.infra.toss.dto;

public record PaymentRequest(int amount, String customerKey, String orderId, String orderName,
                String customerEmail) {
}
